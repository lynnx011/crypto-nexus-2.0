package com.example.cryptocurrency.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrency.R
import com.example.cryptocurrency.adapter.CryptoNewsAdapter
import com.example.cryptocurrency.databinding.FragmentNewsBinding
import com.example.cryptocurrency.presentation.view_models.CryptoNewsViewModel
import com.example.cryptocurrency.utils.collectLatest
import com.example.cryptocurrency.utils.navigateWithBundle
import com.example.cryptocurrency.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var cryptoNewsAdapter: CryptoNewsAdapter
    private val newsViewModel: CryptoNewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        setupRecyclerView()

    }

    private fun observeViewModel() {
            newsViewModel.getCryptoNews("bitcoin")
            newsViewModel.news.collectLatest(viewLifecycleOwner) {
                binding.circular.isVisible = it.loading
                when {
                    it.loading -> {}
                    !it.cryptoNews.isNullOrEmpty() -> {
                        newsViewModel.cryptoNews.value = it.cryptoNews
                    }

                    it.error.isNotEmpty() -> {
                        showToast(it.error)
                    }
                }
            }
    }

    private fun setupRecyclerView() {
        binding.recView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            cryptoNewsAdapter = CryptoNewsAdapter { article ->
                if (!article.url.isNullOrEmpty()) {
                    navigateWithBundle(R.id.nav_news_details, bundleOf("newsUrl" to article.url))
                }
            }
            adapter = cryptoNewsAdapter
        }

        newsViewModel.cryptoNews.collectLatest(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                cryptoNewsAdapter.differ.submitList(it)
            }
        }
    }
}