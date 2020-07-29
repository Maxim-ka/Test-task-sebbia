package com.reschikov.sebbia.testtask.data.net

import android.net.ConnectivityManager
import android.os.Build
import com.reschikov.sebbia.testtask.data.net.model.*
import com.reschikov.sebbia.testtask.data.repository.Requested
import com.reschikov.sebbia.testtask.entity.dot.NoNetWork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NetWorkProvider @Inject constructor(
    private val request: RequestTesttaskSebbia,
    private val connectivity : ConnectivityManager) : Requested {

    @Throws(NoNetWork::class)
    override suspend fun requestCategories(): ReplyList<Category> {
        if (!hasStateNet()) throw NoNetWork()
        return suspendCoroutine {
            request.getCategories().enqueue(getCallBack(it))
        }
    }

    @Throws(NoNetWork::class)
    override suspend fun requestNews(categoryId: Long, page: Int): ReplyList<ShortNews> {
        if (!hasStateNet()) throw NoNetWork()
        return suspendCoroutine {
            request.getListNews(categoryId, page).enqueue(getCallBack(it))
        }
    }

    @Throws(NoNetWork::class)
    override suspend fun requestFullNews(newsId: Long): ReplyNews {
        if (!hasStateNet()) throw NoNetWork()
        return  suspendCoroutine {
            request.getNews(newsId).enqueue(getCallBack(it))
        }
    }

    private fun <T> getCallBack(continuation: Continuation<T>): Callback<T> {
        return object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if(response.isSuccessful){
                    response.body()?.let { continuation.resume(it) }
                } else {
                    response.errorBody()?.let { continuation.resumeWithException(Throwable(it.string())) }
                }
            }
        }
    }

    private fun hasStateNet() : Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return connectivity.activeNetwork != null
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return connectivity.isDefaultNetworkActive
        }
        val networkInfo = connectivity.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }
}