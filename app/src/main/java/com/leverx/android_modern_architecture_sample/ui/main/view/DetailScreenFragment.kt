package com.leverx.android_modern_architecture_sample.ui.main.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.leverx.android_modern_architecture_sample.R
import com.leverx.android_modern_architecture_sample.data.storage.entity.FavoriteArticle
import com.leverx.android_modern_architecture_sample.databinding.FragmentDetailScreenBinding
import com.leverx.android_modern_architecture_sample.ui.main.viewModel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailScreenFragment : Fragment() {

    val args: DetailScreenFragmentArgs by navArgs()
    private val detailViewModel: DetailViewModel by viewModels()

    private var _binding: FragmentDetailScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var favoriteStatus = false
        _binding = FragmentDetailScreenBinding.inflate(inflater, container, false)
        val view = binding.root

        val idArticle = args.idArticle

        val newsItem = detailViewModel.getById(idArticle!!.toInt())
        Log.e("TAG", newsItem.toString())
        val content = newsItem?.article?.content
        val urlImg = newsItem?.article?.urlToImage
        val title = newsItem?.article?.title

        binding.txtScreenDetail.text = content
        context?.let {
            Glide.with(it.applicationContext).load(urlImg).into(binding.imgScreenDetail)
        }
        if (title != null) {
            binding.txtToolbar.text = (
                if (title.length <= 16) {
                    title
                } else {
                    (title?.substring(0, 16)) + "..."
                }
                ).toString()
        }
        binding.txtToolbar.setTextColor(Color.WHITE)
        binding.toolbarDetail.inflateMenu(R.menu.detail_menu)
        binding.toolbarDetail.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbarDetail.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        if (detailViewModel.isFavoriteArticle(title.toString()) != null) {
            favoriteStatus = true
        }
        if (favoriteStatus) {
            binding.toolbarDetail.menu.findItem(R.id.likeNews)
                .setIcon(R.drawable.ic_baseline_favorite_24)
        }
        binding.toolbarDetail.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.likeNews -> {
                    if (detailViewModel.isFavoriteArticle(title.toString()) != null) {

                        detailViewModel.deleteById(FavoriteArticle(title.toString()))
                        favoriteStatus = false
                        binding.toolbarDetail.menu.findItem(R.id.likeNews)
                            .setIcon(R.drawable.ic_baseline_favorite_border_24)
                    } else {

                        favoriteStatus = true
                        binding.toolbarDetail.menu.findItem(R.id.likeNews)
                            .setIcon(R.drawable.ic_baseline_favorite_24)
                        detailViewModel.insertFavoriteArticle(listOf(FavoriteArticle(idArticle)))
                    }
                    true
                }
                else -> {
                    super.onOptionsItemSelected(it)
                }
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }
}
