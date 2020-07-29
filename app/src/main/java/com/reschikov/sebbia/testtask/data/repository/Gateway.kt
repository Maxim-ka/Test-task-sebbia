package com.reschikov.sebbia.testtask.data.repository

import com.reschikov.sebbia.testtask.data.net.model.Category
import com.reschikov.sebbia.testtask.data.net.model.FullNews
import com.reschikov.sebbia.testtask.data.net.model.ShortNews
import javax.inject.Inject

class Gateway @Inject constructor(private val requested: Requested) : Derivable {

    override suspend fun getListCategories(): Pair<List<Category>?, Throwable?> {
        return try {
            Pair(requested.requestCategories().list, null)
        } catch (e : Throwable) {
            Pair(null, e)
        }
    }

    @Throws
    override suspend fun getListNews(categoryId: Long, page: Int) : List<ShortNews> {
        return requested.requestNews(categoryId, page).list
    }

    override suspend fun getFullNews(newsId: Long): Pair<FullNews?, Throwable?> {
        return try {
            Pair(requested.requestFullNews(newsId).fullNews, null)
        } catch (e : Throwable) {
            Pair(null, e)
        }
    }
}