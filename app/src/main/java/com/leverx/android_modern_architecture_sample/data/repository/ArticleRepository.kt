package com.leverx.android_modern_architecture_sample.data.repository

import androidx.annotation.WorkerThread
import androidx.paging.*
import com.leverx.android_modern_architecture_sample.data.network.NewsService
import com.leverx.android_modern_architecture_sample.data.network.models.Article
import com.leverx.android_modern_architecture_sample.data.paging.ArticleRemoteMediator
import com.leverx.android_modern_architecture_sample.data.storage.ArticleRoomDatabase
import com.leverx.android_modern_architecture_sample.data.storage.entity.ArticleDB
import com.leverx.android_modern_architecture_sample.data.storage.entity.ArticleToSource
import com.leverx.android_modern_architecture_sample.data.storage.entity.FavoriteArticle
import com.leverx.android_modern_architecture_sample.data.storage.entity.SourceDB
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepository @Inject constructor(
    private val newsApi: NewsService,
    private val db: ArticleRoomDatabase
) {

    @ExperimentalPagingApi
    fun invoke(): Flow<PagingData<ArticleToSource>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = ArticleRemoteMediator(
                db, newsApi, null
            )
        ) {
            db.articleDao().getPageArticles()
        }.flow
    }

    @ExperimentalPagingApi
    fun invokeFavorite(): Flow<PagingData<FavoriteArticle>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
        ) {
            db.favoriteArticleDao().getPageFavoriteArticles()
        }.flow
    }

    @Suppress("RedundantSuspendModifier")
    suspend fun insert(article: List<Article>?) {
        val listArticle = mutableListOf<ArticleDB>()
        val listSource = mutableListOf<SourceDB>()
        val listFavoriteArticle = mutableListOf<FavoriteArticle>()
        article?.forEach {
            listArticle.add(ArticleDB(it))
            listSource.add(SourceDB(it))
            listFavoriteArticle.add(FavoriteArticle(it.title ?: "title"))
        }
        db.articleDao().insert(listArticle.toList())
        db.sourceDao().insert(listSource.toList())
        db.favoriteArticleDao().insert((listFavoriteArticle.toList()))
    }

    @ExperimentalPagingApi
    fun invokeSearch(text: String): Flow<PagingData<ArticleToSource>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = ArticleRemoteMediator(
                db, newsApi, text
            )
        ) {
            db.articleDao().searchPageText(text)!!
        }.flow
    }

    @Suppress("RedundantSuspendModifier")
    fun getById(searchID: Int): ArticleToSource? {
        return db.articleDao().getById(searchID)
    }

    fun isFavoriteArticle(searchID: String): FavoriteArticle? {
        return db.favoriteArticleDao().isFavoriteById(searchID)
    }

    fun deleteById(article: FavoriteArticle) {
        db.favoriteArticleDao().deleteById(article)
    }

    @Suppress("RedundantSuspendModifier")
    suspend fun insertFavoriteArticle(favoriteArticles: List<FavoriteArticle>) {
        db.favoriteArticleDao().insert(favoriteArticles)
    }

    fun deleteAllFavorite() {
        db.favoriteArticleDao().deleteAll()
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun searchArticleTest(text: String): List<ArticleToSource> {
        return db.articleDao().searchArticleTest(text)
    }
}
