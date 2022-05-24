package com.leverx.android_modern_architecture_sample.ui.main.viewModel

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.leverx.android_modern_architecture_sample.data.network.models.Article
import com.leverx.android_modern_architecture_sample.data.repository.ArticleRepository
import com.leverx.android_modern_architecture_sample.data.storage.entity.ArticleToSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val repository: ArticleRepository
) : ViewModel() {

    @ExperimentalPagingApi
    val newsPostFlow = repository.invoke()

    @ExperimentalPagingApi
    val newsFavoritePostFlow = repository.invokeFavorite()

    @ExperimentalPagingApi
    fun newsPostFlowSearch(text: String): Flow<PagingData<ArticleToSource>> {
        return repository.invokeSearch(text)
    }

    fun insert(article: List<Article>?) = viewModelScope.launch {
        if (article != null) {
            repository.insert(article)
        }
    }

    fun deleteAllSaved() = viewModelScope.launch {
        repository.deleteAllFavorite()
    }

    fun searchArticleTest(text: String): List<ArticleToSource> {
        return repository.searchArticleTest(text)
    }
}
