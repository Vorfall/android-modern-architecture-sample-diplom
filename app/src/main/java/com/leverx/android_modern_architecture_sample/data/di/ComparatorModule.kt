package com.leverx.android_modern_architecture_sample.data.di

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.leverx.android_modern_architecture_sample.data.storage.entity.ArticleToSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ComparatorModule {
    @Singleton
    @Provides
    fun providesNewsComparator(): DiffUtil.ItemCallback<ArticleToSource> {
        return object : DiffUtil.ItemCallback<ArticleToSource>() {
            override fun areItemsTheSame(
                oldItem: ArticleToSource,
                newItem: ArticleToSource
            ): Boolean {
                return oldItem.article!!.title == newItem.article!!.title
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: ArticleToSource,
                newItem: ArticleToSource
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
