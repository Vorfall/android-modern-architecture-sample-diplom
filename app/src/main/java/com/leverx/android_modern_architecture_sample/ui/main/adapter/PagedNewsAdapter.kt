package com.leverx.android_modern_architecture_sample.ui.main.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leverx.android_modern_architecture_sample.R
import com.leverx.android_modern_architecture_sample.data.storage.entity.ArticleToSource
import com.leverx.android_modern_architecture_sample.databinding.NewsItemBinding
import com.leverx.android_modern_architecture_sample.ui.main.view.MainScreenFragmentDirections
import javax.inject.Inject

class PagedNewsAdapter @Inject constructor(
    @set:Inject
    var diffCallback: DiffUtil.ItemCallback<ArticleToSource>
) :
    PagingDataAdapter<ArticleToSource, PagedNewsAdapter.NewsViewHolder>(
        diffCallback
    ) {
    private lateinit var context: Context

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = NewsItemBinding.bind(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        with(holder) {
            val threeDots = "..."

            val newsItem = getItem(position)
            try {
                binding.apply {
                    txtSourceNews.text = newsItem?.source?.name
                    txtHeaderNews.text = if (newsItem!!.article?.title!!.length > 36) {
                        newsItem.article?.title!!.substring(0, 35) + threeDots
                    } else {
                        newsItem.article?.title + threeDots
                    }

                    txtDateNews.text = newsItem.article?.publishedAt?.substring(0, 10)
                    txtDescriptionNews.text = if (newsItem.article?.content!!.length > 70) {
                        newsItem.article?.content?.substring(0, 70) + threeDots
                    } else {
                        newsItem.article?.content + threeDots
                    }
                }

                Glide.with(context)
                    .load(newsItem?.article!!.urlToImage)
                    .into(holder.binding.imgNews)

                binding.cardViewNews.setOnClickListener { view ->
                    navigateToDetail(newsItem, view)
                }
            } catch (e: Exception) {
                Log.e("TAG adapter ", e.toString())

                Log.e("TAG adapter ", newsItem.toString())
            }
        }
    }

    private fun navigateToDetail(articleItem: ArticleToSource, view: View) {
        val directions =
            MainScreenFragmentDirections.actionMainScreenFragmentToDetailScreenFragment(
                articleItem.article!!.id.toString()
            )
        view.findNavController().navigate(directions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item, parent, false)
        context = parent.context
        return NewsViewHolder(view)
    }
}
