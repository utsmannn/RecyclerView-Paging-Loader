## Simplify network loader for paging recyclerview

[ ![Download](https://api.bintray.com/packages/kucingapes/utsman/com.utsman.rvpagingloader/images/download.svg) ](https://bintray.com/kucingapes/utsman/com.utsman.rvpagingloader/_latestVersion) <br>
This library is used to create a progress loader in the recyclerview when calling data.<br>
Based on [Android Paging Library](https://developer.android.com/topic/libraries/architecture/paging), **you must know Paging Library before using it**.


### 1. Step one
***Create 2 ViewHolder*** <br>
Create your item ViewHolder as usual, and create your network loader ViewHolder with extend ```NetworkViewHolder``` <br>
```kotlin
class LoaderViewHolder(view: View) : NetworkViewHolder(view)
```


### 2. Step two
***Create your diffUtil class*** <br>
DiffUtil is a utility class that can calculate the difference between two lists and output a list of update operations that converts the first list into the second one. You can create this class like this:
```kotlin
class ItemDiffUtil : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean = oldItem == newItem
}
```

### 3. Step three
***Modify your RecyclerView Adapter*** <br>
Modify your adapter with extend ```PagingLoaderAdapter```
```kotlin
class MyPagingAdapter(loaderIdentifierId: LoaderIdentifierId) :
    PagingLoaderAdapter<Item, ItemViewHolder, LoaderViewHolder>(ItemDiffUtil(), loaderIdentifierId) {

    override fun onItemCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // return to your item view holder
        return PexelViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false))
    }
    

    override fun onNetworkViewHolder(parent: ViewGroup, viewType: Int): LoaderViewHolder {
        // return to your network loader view holder
        return LoaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loader, parent, false))
    }
        

    override fun onItemBindViewHolder(holder: PexelViewHolder, position: Int) {
        // bind your holder
    }
}
```

### 4. Step four
***Setup in activity/fragment*** <br>
#### 4.1. Create loader identifier builder
```LoaderIdentifierId``` is a class for identifier id of view in your loader xml (ProgressBar and TextView for error code).
```kotlin
val loaderIdentifierId = LoaderIdentifierId.Builder()
        .setIdProgressLoader(R.id.progress_circular)
        .setIdTextViewError(R.id.error_text_view)
        .build()
```
#### 4.2. Define your adapter with loader identifier
```kotlin
val myAdapter = MyAdapter(loaderIdentifierId)
```

#### 4.3. Submit data and network state
Submit your data and network state for loader using ViewModel
```kotlin
viewModel.getItems().observe(this, Observer { dataItems ->
    myAdapter.submitList(dataItems)
})

viewModel.getLoader().observe(this, Observer { networkState ->
    myAdapter.submitNetworkState(networkState)
})
```

For grid layout manager you can fix loader position with
```myAdapter.setGridSpan(column_size)```

### Download
```gradle
implementation 'com.utsman.rvpagingloader:adapter:1.0.0'
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

