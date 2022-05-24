package com.leverx.android_modern_architecture_sample.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.leverx.android_modern_architecture_sample.data.network.NewsService
import com.leverx.android_modern_architecture_sample.data.network.models.News
import com.leverx.android_modern_architecture_sample.data.storage.ArticleRoomDatabase
import com.leverx.android_modern_architecture_sample.data.storage.entity.*
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalPagingApi
class ArticleRemoteMediator(
    private val db: ArticleRoomDatabase,
    private val newsApi: NewsService,
    private val text: String? = null
) :
    RemoteMediator<Int, ArticleToSource>() {
    private var nextPageNumber: Int = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleToSource>
    ): MediatorResult {
        return try {
            val news: Response<News>
            val totalResults: Int?
            if (text == null) {
                news = newsApi.getArticles(
                    SimpleDateFormat("yyyy-MM-dd").format(Date()),
                    page = nextPageNumber
                )
                totalResults = news.body()?.totalResults
            } else {
                news = newsApi.getSearchArticles(
                    page = nextPageNumber,
                    newsCategory = text
                )
                totalResults = news.body()?.totalResults
            }
            when (loadType) {
                LoadType.REFRESH -> {
                    nextPageNumber = 1
                    nextPageNumber
                }
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> {
                    val remoteKey = db.withTransaction {
                        getLastRemoteKey(state)
                    }
                    if (remoteKey?.next == totalResults) {
                        return MediatorResult.Success(true)
                    }
                }
            }
            db.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    db.keysDao().deleteAllRemoteKeys()
                }

                nextPageNumber++
                db.keysDao().insertRemoteKey(
                    ArticleRemoteKey(
                        news.body()!!.articles[news.body()!!.articles.size - 1].title ?: "title",
                        nextPageNumber,
                        (nextPageNumber + 1)
                    )
                )

                val listArticle = mutableListOf<ArticleDB>()
                val listSource = mutableListOf<SourceDB>()
                news.body()!!.articles.forEach {
                    listArticle.add(ArticleDB(it))
                    listSource.add(SourceDB(it))
                }

                db.articleDao().insert(listArticle)
                db.sourceDao().insert(listSource)
            }
            MediatorResult.Success(endOfPaginationReached = nextPageNumber >= news.body()?.totalResults!!)
        } catch (e: Exception) {
            Log.e("TAG Error", e.toString())
            MediatorResult.Error(e)
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, ArticleToSource>): ArticleRemoteKey? {
        return state.lastItemOrNull()?.let {
            db.keysDao().getAllRemoteKey(it.article!!.title)
        }
    }
}
