package com.moidoc.example.android.flickrverysimpleclientexample.ui.common.adapter

typealias AdapterCallback<T> = (clickType: ClickType, data: T) -> Unit

enum class ClickType {
    CLICK, LONG_CLICK
}