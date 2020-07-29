package com.reschikov.sebbia.testtask.presentation.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.reschikov.sebbia.testtask.presentation.ui.OnItemClickListener
import kotlinx.android.extensions.LayoutContainer

abstract class BaseAdapter<T>(private val onItemClickListener: OnItemClickListener<T>) :
    RecyclerView.Adapter<BaseAdapter.BaseViewHolder<T>>() {

    var list: List<T> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.show(list[position])
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder<T>) {
        super.onViewAttachedToWindow(holder)
        holder.containerView.setOnClickListener { onItemClickListener.onItemClick(list[holder.adapterPosition]) }
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder<T>) {
        super.onViewDetachedFromWindow(holder)
        holder.containerView.setOnClickListener(null)
    }

    abstract class BaseViewHolder<T>(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        abstract fun show (item : T)
    }
}