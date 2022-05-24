package com.leverx.android_modern_architecture_sample.data.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leverx.android_modern_architecture_sample.data.network.models.Article

@Entity(tableName = "article_table")

data class ArticleDB(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "publishedAt") val publishedAt: String,
    @ColumnInfo(name = "sourceName") val sourceName: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "urlToImage") val urlToImage: String?
) {
    constructor(article: Article) :
        this(
            id = 0,
            author = article.author ?: "author",
            content = article.content ?: "content",
            description = article.description ?: "description",
            publishedAt = article.publishedAt ?: "publishedAt",
            sourceName = article.source.name ?: "name",
            title = article.title ?: "title",
            url = article.url ?: "url",
            urlToImage = article.urlToImage ?: "urlToImage"
        )
}
