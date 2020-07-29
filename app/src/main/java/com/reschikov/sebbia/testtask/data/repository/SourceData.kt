package com.reschikov.sebbia.testtask.data.repository

import androidx.paging.PagingSource
import com.reschikov.sebbia.testtask.data.net.model.ShortNews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val START_PAGE = 0

class SourceData (private val categoryId : Long, private val derivable: Derivable?) :
    PagingSource<Int, ShortNews>() {

    private lateinit var received : Derivable

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ShortNews> {
        received = derivable ?: return takeEmptyList()
        if (params is LoadParams.Prepend<Int>) return prepend(params)
        if (params is LoadParams.Append<Int>) return append(params)
        return refresh(params)
    }

    private suspend fun refresh(params: LoadParams<Int>) : LoadResult<Int, ShortNews>{
        val page = params.key ?: START_PAGE
        return getListNews(page)
    }

    private suspend fun append(params : LoadParams.Append<Int>) : LoadResult<Int, ShortNews>{
        return getListNews(params.key)
    }

    private suspend fun getListNews(pageKey : Int) : LoadResult<Int, ShortNews> {
        return try {
            request(pageKey).run {
                val prev = if (pageKey == START_PAGE) null else pageKey
                val next = if (isEmpty()) null else pageKey + 1
                LoadResult.Page(this, prev, next)
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private suspend fun prepend(params : LoadParams.Prepend<Int>) : LoadResult<Int, ShortNews>{
        val key = params.key
        return try {
            request(key).run {
                val prev = if (key - 1 < 0) null else key - 1
                LoadResult.Page(this, prev, key)
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    @Throws
    private suspend fun request(key : Int) : List<ShortNews>{
        return withContext(Dispatchers.IO){
            received.getListNews(categoryId, key)
        }
    }

    private fun takeEmptyList() : LoadResult.Page<Int, ShortNews> {
        invalidate()
        return LoadResult.Page(emptyList(), null, null)
    }
}