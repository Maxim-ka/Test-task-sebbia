package com.reschikov.sebbia.testtask.presentation.ui.shortnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.reschikov.sebbia.testtask.data.repository.SourceData
import com.reschikov.sebbia.testtask.data.net.model.ShortNews
import com.reschikov.sebbia.testtask.data.repository.Derivable
import com.reschikov.sebbia.testtask.presentation.ui.base.BaseViewModel
import javax.inject.Inject

private const val PAGE_SIZE = 10
private const val PREFETCH_SIZE = 2

class ListShortNewsViewModel @Inject constructor(derivable: Derivable?) :
    BaseViewModel(derivable) {

    private lateinit var listShortNewsLiveData : LiveData<PagingData<ShortNews>>

    fun getListShortNewsLiveData() : LiveData<PagingData<ShortNews>> = listShortNewsLiveData

    fun createShortNewsLiveData(categoriesId :Long)  {
        createPagingData(categoriesId)
    }

    override fun completeDownload() {}

    private fun createPagingData(categoriesId :Long) {
        val config = createPagingConfig()
        listShortNewsLiveData = Pager(config){
            SourceData(categoriesId, derivable)
        }.liveData.cachedIn(viewModelScope)
    }

    private fun createPagingConfig() : PagingConfig {
        return PagingConfig(PAGE_SIZE, PREFETCH_SIZE, false, PAGE_SIZE, PAGE_SIZE * 2)
    }
}