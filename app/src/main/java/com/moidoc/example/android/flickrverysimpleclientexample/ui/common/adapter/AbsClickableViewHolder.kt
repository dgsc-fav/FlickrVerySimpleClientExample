package com.moidoc.example.android.flickrverysimpleclientexample.ui.common.adapter

import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import androidx.recyclerview.widget.RecyclerView
import com.moidoc.example.android.flickrverysimpleclientexample.R

/**
 * Abstract clickable view holder for recyclerview adapter
 *
 *
 * Created by mr.Dmitry.Korchagin on 07.06.2017.
 */

open class AbsClickableViewHolder(itemView: View, onClick: OnClickListener?, onLongClick: OnLongClickListener?) :
    RecyclerView.ViewHolder(itemView), OnClickListener, OnLongClickListener {

    private var onClickListener: OnClickListener? = null
    private var onLongClickListener: OnLongClickListener? = null
    var root: View? = null

    init {

        onClickListener = onClick
        onLongClickListener = onLongClick

        root = itemView.findViewById(R.id.root)

        root?.let { root ->
            var hasListener = false
            if (onClickListener != null) {
                root.setOnClickListener(this)
                hasListener = true
            }
            if (onLongClickListener != null) {
                root.setOnLongClickListener(this)
                hasListener = true
            }
            if (!hasListener) {
                root.isClickable = false
            }
        }
    }

    override fun onClick(v: View) {
        onClickListener?.let {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                v.setTag(R.id.tag_position, position)
                it.onClick(v)
            }
        }
    }

    override fun onLongClick(v: View): Boolean {
        onLongClickListener?.let {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                v.setTag(R.id.tag_position, position)
                return it.onLongClick(v)
            }
        }

        return false
    }
}