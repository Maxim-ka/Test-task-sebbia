package com.reschikov.sebbia.testtask.presentation.ui.shortnews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.reschikov.sebbia.testtask.R
import com.reschikov.sebbia.testtask.data.net.model.ShortNews
import com.reschikov.sebbia.testtask.presentation.ui.OnItemClickListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_shortnews.view.*
import javax.inject.Inject

class ShortNewsAdapter @Inject constructor(
    private val onItemClickListener: OnItemClickListener<ShortNews>,
    diffCallback: DiffUtilItemCallback) :
    PagingDataAdapter<ShortNews, ShortNewsAdapter.ShortNewsViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortNewsViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_shortnews, parent, false)
        return ShortNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShortNewsViewHolder, position: Int) {
        getItem(position)?.let { holder.show(it) }
    }

    override fun onViewAttachedToWindow(holder: ShortNewsViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.containerView.setOnClickListener {
            getItem(holder.bindingAdapterPosition)?.let { onItemClickListener.onItemClick(it) }
        }
    }

    override fun onViewDetachedFromWindow(holder: ShortNewsViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.containerView.setOnClickListener(null)
    }

    class ShortNewsViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer{

        fun show(item: ShortNews) {
            with(containerView){
                item.also {
                    tv_title.text = it.title
                    tv_short_description.text = it.shortDescription
                    tv_date.text = it.date
                }
            }
        }
    }
}