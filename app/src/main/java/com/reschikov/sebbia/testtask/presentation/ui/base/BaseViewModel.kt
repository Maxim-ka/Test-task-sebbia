package com.reschikov.sebbia.testtask.presentation.ui.base

import androidx.lifecycle.*
import com.reschikov.sebbia.testtask.data.repository.Derivable

abstract class BaseViewModel (protected var derivable: Derivable?) : ViewModel() {

    protected val error = MutableLiveData<Throwable?>()
    protected val isVisibleProgress = MutableLiveData<Boolean>()

    fun getErrorMsg() : LiveData<Throwable?> = error
    fun isVisibleProgress() : LiveData<Boolean> = isVisibleProgress

    fun checkNet() {
        completeDownload()
    }

    abstract fun completeDownload()

    override fun onCleared() {
        super.onCleared()
        derivable = null
    }
}