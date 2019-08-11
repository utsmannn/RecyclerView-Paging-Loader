## Simplify network loader for paging recyclerview

[ ![Download](https://api.bintray.com/packages/kucingapes/utsman/com.utsman.rvpagingloader/images/download.svg) ](https://bintray.com/kucingapes/utsman/com.utsman.rvpagingloader/_latestVersion) <br>
This library is used to create a progress loader in the recyclerview when calling data.<br>
Compatible with [Android Paging Library](https://developer.android.com/topic/libraries/architecture/paging) **(you must know Paging Library before using it)** and endless recyclerview scroll listener (old way, **not recommended**)

| loader state  | error state |
|---| --- |
|  ![](https://i.ibb.co/h15LBG7/Screen-Shot-2019-08-11-at-1-16-38-AM-1.png) | ![](https://i.ibb.co/9b4wb8S/Screenshot-20190811-011505-1.png)  |

### 1. Step one
***Create 2 ViewHolder*** <br>
Create your item ViewHolder as usual, and create your network loader ViewHolder with extend ```NetworkViewHolder``` <br>
```kotlin
class LoaderViewHolder(view: View) : NetworkViewHolder(view)
```


### 2. Step two
***Create your diffUtil class for paging library method <br> For endless scroll listener method, ignore this step*** <br>
DiffUtil is a utility class that can calculate the difference between two lists and output a list of update operations that converts the first list into the second one. You can create this class like this:
```kotlin
class ItemDiffUtil : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean = oldItem == newItem
}
```

### 3. Step three
***Create loader xml***
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:gravity="center"
    android:orientation="vertical">

    <ProgressBar
        android:id="@+id/progress_circular"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="12dp"
        tools:ignore="UnusedAttribute" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textColor="@android:color/holo_red_light"
        android:id="@+id/error_text_view"
        android:layout_margin="12dp"/>

</LinearLayout>
```

### 4. Step four
***Modify your RecyclerView Adapter*** <br>
Modify your adapter with extend ```PagingLoaderAdapter``` or ```EndlessLeaderAdapter```<br>

**Paging library method:**
```kotlin
class MyPagingAdapter(loaderIdentifierId: LoaderIdentifierId) :
    PagingLoaderAdapter<Item, ItemViewHolder, LoaderViewHolder>(ItemDiffUtil(), loaderIdentifierId) {

    override fun onItemCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // return to your item view holder
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false))
    }
    

    override fun onNetworkViewHolder(parent: ViewGroup, viewType: Int): LoaderViewHolder {
        // return to your network loader view holder
        return LoaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loader, parent, false))
    }
        

    override fun onItemBindViewHolder(holder: ItemViewHolder, position: Int) {
        // bind your holder
    }
}
```


**Scroll listener method:**
```kotlin
class MyPagingAdapter(loaderIdentifierId: LoaderIdentifierId) :
    EndlessLoaderAdapter<Item, ItemViewHolder, LoaderViewHolder>(loaderIdentifierId) {

    override fun onItemCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // return to your item view holder
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false))
    }
    

    override fun onNetworkViewHolder(parent: ViewGroup, viewType: Int): LoaderViewHolder {
        // return to your network loader view holder
        return LoaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loader, parent, false))
    }
        

    override fun onItemBindViewHolder(holder: ItemViewHolder, position: Int) {
        // bind your holder
    }
}
```

### 5. Step five
***Setup in activity/fragment*** <br>
#### 5.1. Create loader identifier builder
```LoaderIdentifierId``` is a class for id of view identifier in your loader xml (ProgressBar and TextView for error code).
```kotlin
val loaderIdentifierId = LoaderIdentifierId.Builder()
        .setIdProgressLoader(R.id.progress_circular)
        .setIdTextViewError(R.id.error_text_view)
        .build()
```
#### 5.2. Define your adapter with loader identifier
```kotlin
val myAdapter = MyAdapter(loaderIdentifierId)
```

#### 5.3. Submit data and network state

***Paging Library*** <br>
Submit your data and network state for loader using ViewModel
```kotlin
viewModel.getItems().observe(this, Observer { dataItems ->
    myAdapter.submitList(dataItems)
})

viewModel.getLoader().observe(this, Observer { networkState ->
    myAdapter.submitNetworkState(networkState)
})
```

***Endless Scroll Listener*** <br>
For endless scroll listener, you must create function for call api and setup recyclerview scroll listener with ```EndlessScrollListener(layoutManager)``` like this:
```kotlin
// call api for first page
setupData(endlessAdapter, 1)

// setup scroll listener and get current page
main_recycler_view.addOnScrollListener(object : EndlessScrollListener(layoutManager) {
    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
    
        // call api again with current page + 1
        setupData(endlessAdapter, page+1)
    }

})
```

Submit data and network state in method ```setupData``` like this:
```kotlin
private fun setupData(endlessAdapter: MyEndlessAdapter, page: Int) {
    // submit first network state with loading state
    endlessAdapter.submitNetworkState(NetworkState.LOADING)
    
    // call data from api or local
    RetrofitInstance.create().getCuratedPhoto(10, page)
        .enqueue(object : Callback<Responses> {
            override fun onFailure(call: Call<Responses>, t: Throwable) {
                // submit network error
                endlessAdapter.submitNetworkState(NetworkState.error(t.localizedMessage))
            }

            override fun onResponse(call: Call<Responses>, response: Response<Responses>) {
                // submit new list and network state loaded
                endlessAdapter.submitList(response.body()?.photos)
                endlessAdapter.submitNetworkState(NetworkState.LOADED)
            }

        })
}
```

### Fix progressBar position for grid layout
For grid layout manager you can fix loader position with
```myAdapter.setGridSpan(column_size)```
```kotlin
layoutManager.spanSizeLookup = myAdapter.setGridSpan(COLUMN_SIZE)
```

### Custom NetworkViewHolder
You can create custom network view holder (complex layout loader xml) with override method ```onLoaderBindViewHolder```
```kotlin
override fun onLoaderBindViewHolder(holder: NetworkViewHolder, position: Int, loaderIdentifierRes: LoaderIdentifierId) {
    // bind your custom holder
}
```

### Sample
[Pexel app paging library](https://github.com/utsmannn/RecyclerView-Paging-Loader/tree/master/app/src/main/java/com/utsman/rvpagingloader) <br>
[Pexel app endless scroll listener](https://github.com/utsmannn/RecyclerView-Paging-Loader/tree/master/app_endless/src/main/java/com/utsman/rvpagingloader/endless)

### Download
#### For Paging Library
```gradle
implementation 'com.utsman.rvpagingloader:adapter:1.0.1'
```

#### For Endless Scroll Listener (Not recommended)
```gradle
implementation 'com.utsman.rvpagingloader-ext:adapter-endless:1.0.1'
```


---
```
Copyright 2019 Muhammad Utsman

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

