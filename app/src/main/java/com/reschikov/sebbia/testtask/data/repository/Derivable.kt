package com.reschikov.sebbia.testtask.data.repository

import com.reschikov.sebbia.testtask.data.net.model.Category
import com.reschikov.sebbia.testtask.data.net.model.FullNews
import com.reschikov.sebbia.testtask.data.net.model.ShortNews


interface Derivable {

    suspend fun getListCategories() : Pair<List<Category>?, Throwable?>
    suspend fun getListNews(categoryId : Long, page : Int) : List<ShortNews>
    suspend fun getFullNews(newsId : Long) : Pair<FullNews?, Throwable?>
}