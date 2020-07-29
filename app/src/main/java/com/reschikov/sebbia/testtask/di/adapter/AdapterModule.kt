package com.reschikov.sebbia.testtask.di.adapter

import android.annotation.SuppressLint
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.reschikov.sebbia.testtask.data.net.model.ShortNews
import com.reschikov.sebbia.testtask.presentation.ui.OnItemClickListener
import com.reschikov.sebbia.testtask.presentation.ui.categories.CategoriesAdapter
import com.reschikov.sebbia.testtask.presentation.ui.categories.CategoriesFragment
import com.reschikov.sebbia.testtask.presentation.ui.shortnews.ShortNewsAdapter
import com.reschikov.sebbia.testtask.presentation.ui.shortnews.ListShortNewsFragment
import com.reschikov.sebbia.testtask.presentation.ui.shortnews.DiffUtilItemCallback
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class AdapterModule {

    companion object {

        @SuppressLint("JvmStaticProvidesInObjectDetector")
        @JvmStatic
        @ScopeFragment
        @Provides
        fun provideCategoriesAdapter(fragment: CategoriesFragment) : CategoriesAdapter {
            return CategoriesAdapter(fragment)
        }
    }

    @ScopeFragment
    @Binds
    abstract fun bindDiffUtilItemCallback(diffUtilItemCallback : DiffUtilItemCallback) :
            DiffUtil.ItemCallback<ShortNews>

    @ScopeFragment
    @Binds
    abstract fun bindListShortNewsFragment(fragmentList: ListShortNewsFragment) :
            OnItemClickListener<ShortNews>

    @ScopeFragment
    @Binds
    abstract fun bindShortNewsAdapter(shortNewsAdapter : ShortNewsAdapter) :
            PagingDataAdapter<ShortNews, ShortNewsAdapter.ShortNewsViewHolder>

}