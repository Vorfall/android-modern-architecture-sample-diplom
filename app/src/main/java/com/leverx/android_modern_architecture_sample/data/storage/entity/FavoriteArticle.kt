package com.leverx.android_modern_architecture_sample.data.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "saved_article_table"
)
data class FavoriteArticle(
    @PrimaryKey val id: String,
    val date: String = "Date"
)
