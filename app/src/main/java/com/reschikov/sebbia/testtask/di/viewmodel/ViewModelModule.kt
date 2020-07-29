package com.reschikov.sebbia.testtask.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.reschikov.sebbia.testtask.presentation.ui.base.ViewModelFactory
import com.reschikov.sebbia.testtask.presentation.ui.fullnews.FullNewsViewModel
import com.reschikov.sebbia.testtask.presentation.ui.shortnews.ListShortNewsViewModel
import com.reschikov.sebbia.testtask.presentation.ui.categories.CategoriesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    fun bindCategoriesViewModel(viewModel: CategoriesViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListShortNewsViewModel::class)
    fun bindListShortNewsViewModel(viewModel: ListShortNewsViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FullNewsViewModel::class)
    fun bindFullNewsViewModel(viewModel: FullNewsViewModel) : ViewModel

    @Singleton
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory
}