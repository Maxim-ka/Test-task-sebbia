package com.reschikov.sebbia.testtask.presentation.ui.shortnews

import androidx.recyclerview.widget.DiffUtil
import com.reschikov.sebbia.testtask.data.net.model.ShortNews
import com.reschikov.sebbia.testtask.di.adapter.ScopeFragment
import javax.inject.Inject

@ScopeFragment
class DiffUtilItemCallback @Inject constructor(): DiffUtil.ItemCallback<ShortNews>(){


    override fun areItemsTheSame(oldItem: ShortNews, newItem: ShortNews): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShortNews, newItem: ShortNews): Boolean {
        return oldItem == newItem
    }
}