package com.moidoc.example.android.flickrverysimpleclientexample.ui.list

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.moidoc.example.android.flickrverysimpleclientexample.R
import com.moidoc.example.android.flickrverysimpleclientexample.ui.common.OnLoadListener
import com.moidoc.example.android.flickrverysimpleclientexample.ui.common.adapter.AbsClickableViewHolder
import com.moidoc.example.android.flickrverysimpleclientexample.ui.common.adapter.AbsSortedAdapter
import com.moidoc.example.android.flickrverysimpleclientexample.ui.common.adapter.AdapterCallback

class PhotosListAdapter<VH : RecyclerView.ViewHolder>(context: Context, adapterCallback: AdapterCallback<PhotosListItem>) :
    AbsSortedAdapter<PhotosListItem, VH>(context, adapterCallback) {

    var onLoadListener: OnLoadListener? = null
    private var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    override fun createItems(adapter: AbsSortedAdapter<PhotosListItem, VH>): SortedList<PhotosListItem> {
        return SortedList(PhotosListItem::class.java, object : SortedListAdapterCallback<PhotosListItem>(this) {

            override fun compare(o1: PhotosListItem, o2: PhotosListItem): Int {
                // no sorting
                return 0
            }

            override fun areItemsTheSame(item1: PhotosListItem, item2: PhotosListItem): Boolean {
                return item1.photoId == item2.photoId
            }

            override fun areContentsTheSame(oldItem: PhotosListItem, newItem: PhotosListItem): Boolean {
                return oldItem == newItem
            }
        })
    }

    override fun onView(h: VH, item: PhotosListItem, pos: Int) {
        h as PhotosListItemHolder

        item.adapterPosition = h.getAdapterPosition()
        item.sharedView = h.image
        // the unique transition name as string representation of the item id
        h.image.transitionName = item.photoId

        h.progress.visibility = View.VISIBLE

        Glide.with(context)
            .load(item.url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    h.progress.visibility = View.GONE
                    // also dispatch an event that image loaded with fail
                    // we do not carry about loading result we just wonder about this process is finish
                    onLoadListener?.onLoadOrError(item.photoId)

                    h.image.setImageResource(android.R.drawable.ic_menu_report_image)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    h.progress.visibility = View.GONE
                    // also dispatch an event that image loaded successfully
                    // we do not carry about loading result we just wonder about this process is finish
                    onLoadListener?.onLoadOrError(item.photoId)
                    return false
                }
            })
            .into(h.image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return PhotosListItemHolder(
            layoutInflater.inflate(R.layout.photos_list_item, parent, false),
            onClickReturnItem,
            onLongClickReturnItem
        ) as VH
    }

    class PhotosListItemHolder(
        itemView: View,
        onClick: View.OnClickListener,
        onLongClick: View.OnLongClickListener
    ) : AbsClickableViewHolder(itemView, onClick, onLongClick) {

        val image: ImageView = itemView.findViewById(R.id.item_image)
        val progress: View = itemView.findViewById(R.id.item_progress)

        init {
            progress.visibility = View.VISIBLE
        }
    }
}
