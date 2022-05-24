package com.leverx.android_modern_architecture_sample.ui.main.viewModel

import androidx.lifecycle.*
import com.leverx.android_modern_architecture_sample.data.network.models.Article
import com.leverx.android_modern_architecture_sample.data.repository.ArticleRepository
import com.leverx.android_modern_architecture_sample.data.storage.entity.ArticleToSource
import com.leverx.android_modern_architecture_sample.data.storage.entity.FavoriteArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: ArticleRepository
) : ViewModel() {

    fun getById(searchId: Int): ArticleToSource? {
        return repository.getById(searchId)
    }

    fun isFavoriteArticle(searchId: String): FavoriteArticle? {
        return repository.isFavoriteArticle(searchId)
    }

    fun deleteById(article: FavoriteArticle) {
        repository.deleteById(article)
    }

    fun insertFavoriteArticle(article: List<FavoriteArticle>) = viewModelScope.launch {
        repository.insertFavoriteArticle(article)
    }
}
