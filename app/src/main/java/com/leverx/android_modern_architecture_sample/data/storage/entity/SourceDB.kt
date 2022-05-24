package com.leverx.android_modern_architecture_sample.data.storage.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leverx.android_modern_architecture_sample.data.network.models.Article

@Entity(tableName = "source_table")
data class SourceDB(
    @ColumnInfo(name = "id") val id: String?,
    @PrimaryKey @NonNull @ColumnInfo(name = "name") val name: String,
) {
    constructor(article: Article) :
        this(

            id = article.source.id ?: "id",
            name = article.source.name ?: "name"
        )
}
