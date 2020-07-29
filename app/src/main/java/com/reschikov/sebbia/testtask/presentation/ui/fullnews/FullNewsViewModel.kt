package com.reschikov.sebbia.testtask.presentation.ui.fullnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.reschikov.sebbia.testtask.data.repository.Derivable
import com.reschikov.sebbia.testtask.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FullNewsViewModel @Inject constructor(derivable: Derivable?) :
    BaseViewModel(derivable) {

    private val fullNewsLiveData = MutableLiveData<String?>()
    private var newsId : Long? = null

    fun getFullNewsLiveData() : LiveData<String?> = fullNewsLiveData

    fun getFullNews(newsId : Long) {
        this.newsId = newsId
        loadFullNews(newsId)
    }

    override fun completeDownload() {
        newsId?.let { loadFullNews(it) }
    }

    private fun loadFullNews(newsId : Long) {
        isVisibleProgress.value = true
        viewModelScope.launch(Dispatchers.IO) {
            derivable?.getFullNews(newsId)?.run {
                fullNewsLiveData.postValue(first?.fullDescription)
                error.postValue(second)
                isVisibleProgress.postValue(false)
            }
        }
    }
}