package com.leverx.android_modern_architecture_sample.data.storage.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.leverx.android_modern_architecture_sample.data.storage.entity.FavoriteArticle

@Dao
interface FavoriteArticleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entities: List<FavoriteArticle>)

    @Query("DELETE FROM saved_article_table")
    fun deleteAll()

    @Query("SELECT * FROM saved_article_table WHERE id = :searchId")
    fun isFavoriteById(searchId: String): FavoriteArticle?

    @Delete
    fun deleteById(article: FavoriteArticle)

    @Query("SELECT * FROM saved_article_table")
    fun getPageFavoriteArticles(): PagingSource<Int, FavoriteArticle>
}
