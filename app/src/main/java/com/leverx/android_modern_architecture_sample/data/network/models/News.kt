package com.leverx.android_modern_architecture_sample.data.network.models

data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
