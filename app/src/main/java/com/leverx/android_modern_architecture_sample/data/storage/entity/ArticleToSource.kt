package com.leverx.android_modern_architecture_sample.data.storage.entity

import androidx.room.Embedded
import androidx.room.Relation

class ArticleToSource {
    @Embedded
    var article: ArticleDB? = null

    @Relation(parentColumn = "sourceName", entityColumn = "name")
    var source: SourceDB? = null
}
