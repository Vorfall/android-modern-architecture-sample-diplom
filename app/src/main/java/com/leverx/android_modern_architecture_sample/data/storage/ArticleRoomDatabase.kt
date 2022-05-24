package com.leverx.android_modern_architecture_sample.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.leverx.android_modern_architecture_sample.data.storage.dao.ArticleDao
import com.leverx.android_modern_architecture_sample.data.storage.dao.FavoriteArticleDao
import com.leverx.android_modern_architecture_sample.data.storage.dao.RemoteKeyDao
import com.leverx.android_modern_architecture_sample.data.storage.dao.SourceDao
import com.leverx.android_modern_architecture_sample.data.storage.entity.ArticleDB
import com.leverx.android_modern_architecture_sample.data.storage.entity.ArticleRemoteKey
import com.leverx.android_modern_architecture_sample.data.storage.entity.FavoriteArticle
import com.leverx.android_modern_architecture_sample.data.storage.entity.SourceDB

@Database(
    entities = [ArticleDB::class, SourceDB::class, FavoriteArticle::class, ArticleRemoteKey::class],
    version = 10,
    exportSchema = true
)
abstract class ArticleRoomDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun sourceDao(): SourceDao
    abstract fun favoriteArticleDao(): FavoriteArticleDao
    abstract fun keysDao(): RemoteKeyDao
}

val MIGRATION_7_8 = object : Migration(7, 8) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE saved_article_table ADD COLUMN date TEXT NOT NULL DEFAULT 'Date' ")
    }
}
