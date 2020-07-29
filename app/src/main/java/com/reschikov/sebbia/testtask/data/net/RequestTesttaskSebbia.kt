package com.reschikov.sebbia.testtask.data.net

import com.reschikov.sebbia.testtask.data.net.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RequestTesttaskSebbia {

    @GET("v1/news/categories")
    fun getCategories() : Call<ReplyList<Category>>

    @GET("v1/news/categories/{id}/news")
    fun getListNews(@Path("id") id : Long,
                    @Query("page") page : Int?) : Call<ReplyList<ShortNews>>

    @GET("v1/news/details")
    fun getNews(@Query("id") id : Long) : Call<ReplyNews>
}