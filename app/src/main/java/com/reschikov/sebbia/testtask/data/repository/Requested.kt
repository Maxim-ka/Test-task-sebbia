package com.reschikov.sebbia.testtask.data.repository

import com.reschikov.sebbia.testtask.data.net.model.*


interface Requested {

    suspend fun requestCategories() : ReplyList<Category>
    suspend fun requestNews(categoryId : Long, page : Int) : ReplyList<ShortNews>
    suspend fun requestFullNews(newsId : Long) : ReplyNews
}