package com.reschikov.sebbia.testtask.di

import com.reschikov.sebbia.testtask.data.repository.Derivable
import com.reschikov.sebbia.testtask.data.repository.Gateway
import com.reschikov.sebbia.testtask.di.adapter.AdapterModule
import com.reschikov.sebbia.testtask.di.adapter.ScopeFragment
import com.reschikov.sebbia.testtask.presentation.ui.base.BaseFragment
import com.reschikov.sebbia.testtask.presentation.ui.fullnews.FullNewsFragment
import com.reschikov.sebbia.testtask.presentation.ui.shortnews.ListShortNewsFragment
import com.reschikov.sebbia.testtask.presentation.ui.categories.CategoriesFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindDerivable(gateway: Gateway) : Derivable

    @ContributesAndroidInjector
    abstract fun fullNewsInjector () : FullNewsFragment

    @ScopeFragment
    @ContributesAndroidInjector(modules = [AdapterModule::class])
    abstract fun categoriesInjector () : CategoriesFragment

    @ScopeFragment
    @ContributesAndroidInjector(modules = [AdapterModule::class])
    abstract fun listShortNewsInjector () : ListShortNewsFragment

    @ContributesAndroidInjector
    abstract fun baseFragmentInjector () : BaseFragment
}