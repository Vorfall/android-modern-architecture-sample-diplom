package com.leverx.android_modern_architecture_sample.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leverx.android_modern_architecture_sample.data.storage.entity.ArticleRemoteKey

@Dao
interface RemoteKeyDao {

    @Query("SELECT * FROM article_remote_key_table WHERE id = :id")
    suspend fun getAllRemoteKey(id: String): ArticleRemoteKey?

    @Query("DELETE FROM article_remote_key_table")
    suspend fun deleteAllRemoteKeys()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKey(key: ArticleRemoteKey)
}
