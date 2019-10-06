package com.moidoc.example.android.flickrverysimpleclientexample.ui.common.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.moidoc.example.android.flickrverysimpleclientexample.R

/**
 * An abstract adapter with clickable items
 */
abstract class AbsSortedAdapter<E, H : RecyclerView.ViewHolder>(
    protected val context: Context,
    adapterCallback: AdapterCallback<E>?
) : RecyclerView.Adapter<H>() {

    var items: SortedList<E>
        protected set
    protected val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    protected val onClickReturnItem: View.OnClickListener = View.OnClickListener { view ->
        val clickType = if (view.getTag(R.id.tag_click_type) != null) {
            view.getTag(R.id.tag_click_type) as ClickType
        } else {
            ClickType.CLICK
        }

        onData(clickType, getItem(view.getTag(R.id.tag_position) as Int))
    }
    protected val onLongClickReturnItem: View.OnLongClickListener = View.OnLongClickListener { view ->
        onData(ClickType.LONG_CLICK, getItem(view.getTag(R.id.tag_position) as Int))
        true
    }

    protected var adapterCallback: AdapterCallback<E>? = null

    init {
        this.items = createItems(this)
        this.adapterCallback = adapterCallback
    }

    protected abstract fun createItems(adapter: AbsSortedAdapter<E, H>): SortedList<E>

    protected abstract fun onView(h: H, item: E, pos: Int)

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: H, position: Int) {
        holder.itemView.setTag(R.id.tag_position, position)
        onView(holder, getItem(position), position)
    }

    fun getItem(position: Int): E {
        return items.get(position)
    }

    override fun getItemCount(): Int {
        return items.size()
    }

    fun addItems(list: List<E>) {
        items.addAll(list)
    }

    open fun addItem(item: E) {
        items.add(item)
    }

    fun setItems(list: List<E>) {
        items.clear()
        addItems(list)
    }

    fun clear() {
        items.clear()
    }

    protected fun onData(clickType: ClickType, data: E): Boolean {
        adapterCallback?.let { callback ->
            callback.invoke(clickType, data)
            return true
        }
        return false
    }
}
