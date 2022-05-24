package com.leverx.android_modern_architecture_sample.data.storage.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leverx.android_modern_architecture_sample.data.storage.entity.ArticleDB
import com.leverx.android_modern_architecture_sample.data.storage.entity.ArticleToSource
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(articleDB: List<ArticleDB>)

    @Query("DELETE FROM article_table")
    fun deleteAll()

    @Query("SELECT * FROM article_table WHERE id = :searchId")
    fun getById(searchId: Int): ArticleToSource?

    @Query("SELECT * FROM article_table")
    fun getPageArticles(): PagingSource<Int, ArticleToSource>

    @Query("SELECT * FROM article_table WHERE id = :text")
    fun searchArticleTest(text: String): List<ArticleToSource>

    @Query("SELECT * FROM article_table WHERE content LIKE '%' || :text || '%' or title LIKE '%' || :text || '%' or author LIKE '%' || :text || '%'")
    fun searchPageText(text: String): PagingSource<Int, ArticleToSource>?
}
