package com.reschikov.sebbia.testtask.presentation.ui.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.reschikov.sebbia.testtask.R
import com.reschikov.sebbia.testtask.data.net.model.Category
import com.reschikov.sebbia.testtask.presentation.ui.OnItemClickListener
import com.reschikov.sebbia.testtask.presentation.ui.base.BaseAdapter
import kotlinx.android.synthetic.main.item_category.view.*

class CategoriesAdapter (onItemClickListener: OnItemClickListener<Category>) :
    BaseAdapter<Category>(onItemClickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Category> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    class CategoryViewHolder(view : View) : BaseViewHolder<Category>(view) {

        override fun show(item: Category) {
            itemView.tv_category.text = item.name
        }
    }
}