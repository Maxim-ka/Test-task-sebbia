package com.reschikov.sebbia.testtask.presentation.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.reschikov.sebbia.testtask.data.net.model.Category
import com.reschikov.sebbia.testtask.data.repository.Derivable
import com.reschikov.sebbia.testtask.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(derivable: Derivable?)
    : BaseViewModel(derivable) {

    private val dataListCategories = MutableLiveData<List<Category>>()

    init {
        loadData()
    }

    fun getLivDataListCategories(): LiveData<List<Category>> = dataListCategories

    override fun completeDownload() {
        loadData()
    }

    private fun loadData(){
        isVisibleProgress.value = true
        viewModelScope.launch(Dispatchers.IO) {
            derivable?.getListCategories()?.let {
                it.first?.let { list ->
                    dataListCategories.postValue(list)
                }
                error.postValue(it.second)
                isVisibleProgress.postValue(false)
            }
        }
    }
}