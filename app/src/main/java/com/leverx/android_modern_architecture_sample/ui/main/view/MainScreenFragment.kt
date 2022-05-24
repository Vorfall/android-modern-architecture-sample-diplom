package com.leverx.android_modern_architecture_sample.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.leverx.android_modern_architecture_sample.R
import com.leverx.android_modern_architecture_sample.ui.compose.MainComposeActivity
import com.leverx.android_modern_architecture_sample.databinding.FragmentMainScreenBinding
import com.leverx.android_modern_architecture_sample.ui.main.adapter.PagedNewsAdapter
import com.leverx.android_modern_architecture_sample.ui.main.viewModel.ArticleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@AndroidEntryPoint
class MainScreenFragment : Fragment() {

    private val KEY_RECYCLER_STATE = "recycler_state"
    private var mBundleRecyclerViewState: Bundle? = null

    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    private val newsViewModel: ArticleViewModel by viewModels()

    @Inject
    lateinit var pagedNewsAdapter: PagedNewsAdapter

    @Inject
    lateinit var pagedSearchNewsAdapter: PagedNewsAdapter

    @Inject
    lateinit var pagedFavoriteNewsAdapter: PagedNewsAdapter

    @OptIn(ExperimentalPagingApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var flatFavorite = false
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.swipeRecycler.setOnRefreshListener {

            pagedNewsAdapter.refresh()
            pagedNewsAdapter.hasObservers()
            var temp = pagedNewsAdapter.refresh().toString()
        }

        binding.toolbarMainScreen.inflateMenu(R.menu.main_menu)
        binding.toolbarMainScreen.menu.findItem(R.id.likeNews)
            .setIcon(R.drawable.ic_baseline_favorite_border_24)
        binding.toolbarMainScreen.setOnMenuItemClickListener { it ->
            when (it.itemId) {
                R.id.compose -> {
                    val intent = Intent(context, MainComposeActivity::class.java)
                    intent.putExtra("login", "unknown")
                    startActivity(intent)
                    true
                }
                R.id.likeNews -> {
                    if (!flatFavorite) {

                        initializeFavoritePagedNewsRecycler()
                        viewLifecycleOwner.lifecycleScope.launch {
                            newsViewModel.newsFavoritePostFlow
                                .collectLatest {

                                    val articleToSource = it.flatMap { e ->
                                        newsViewModel.searchArticleTest(e.id)
                                    }
                                    pagedFavoriteNewsAdapter.submitData(articleToSource)
                                }
                        }
                        binding.toolbarMainScreen.menu.findItem(R.id.likeNews)
                            .setIcon(R.drawable.ic_baseline_favorite_24)

                        flatFavorite = true
                    } else {
                        binding.toolbarMainScreen.menu.findItem(R.id.likeNews)
                            .setIcon(R.drawable.ic_baseline_favorite_border_24)
                        flatFavorite = false
                        binding.news.layoutManager = LinearLayoutManager(context)
                        binding.news.adapter = pagedNewsAdapter
                        pagedNewsAdapter.refresh()
                    }
                    true
                }
                R.id.searchNews -> {
                    initializeSearchPagedNewsRecycler()
                    viewLifecycleOwner.lifecycleScope.launch {
                        newsViewModel.newsPostFlowSearch(binding.editTextSearch.text.toString())
                            .collectLatest {
                                pagedSearchNewsAdapter.submitData(it)
                            }
                    }
                    true
                }

                R.id.delete -> {
                    newsViewModel.deleteAllSaved()

                    true
                }
                else -> {
                    super.onOptionsItemSelected(it)
                }
            }
        }

        return view
    }

    override fun onPause() {
        super.onPause()
        mBundleRecyclerViewState = Bundle()
        val listState: Parcelable = binding.news.layoutManager!!.onSaveInstanceState()!!
        mBundleRecyclerViewState!!.putParcelable(KEY_RECYCLER_STATE, listState)
    }

    override fun onResume() {
        super.onResume()
        if (mBundleRecyclerViewState != null) {
            val listState = mBundleRecyclerViewState!!.getParcelable<Parcelable>(KEY_RECYCLER_STATE)
            binding.news.layoutManager!!.onRestoreInstanceState(listState)
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        mBundleRecyclerViewState = savedInstanceState
        val listState: Parcelable = binding.news.layoutManager!!.onSaveInstanceState()!!
        mBundleRecyclerViewState!!.putParcelable(KEY_RECYCLER_STATE, listState)
        super.onSaveInstanceState(savedInstanceState)
    }



    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializePagedNewsRecycler()

        viewLifecycleOwner.lifecycleScope.launch {
            newsViewModel.newsPostFlow.collectLatest {
                pagedNewsAdapter.submitData(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            pagedNewsAdapter.loadStateFlow.collectLatest { loadStates ->
                val refreshState = loadStates.refresh
                binding.swipeRecycler.isRefreshing = false
                binding.news.isVisible = refreshState !is LoadState.Loading
            }
        }
    }

    private fun initializeFavoritePagedNewsRecycler() {
        val stateAdapter = pagedFavoriteNewsAdapter
        binding.news.layoutManager = LinearLayoutManager(context)
        binding.news.adapter = stateAdapter
    }

    private fun initializeSearchPagedNewsRecycler() {
        val stateAdapter = pagedSearchNewsAdapter
        binding.news.layoutManager = LinearLayoutManager(context)
        binding.news.adapter = stateAdapter
    }

    private fun initializePagedNewsRecycler() {
        val stateAdapter = pagedNewsAdapter
        binding.news.layoutManager = LinearLayoutManager(context)
        binding.news.adapter = stateAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
