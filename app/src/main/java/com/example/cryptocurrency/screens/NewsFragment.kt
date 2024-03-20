package com.example.cryptocurrency.screens
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrency.R
import com.example.cryptocurrency.adapter.CryptoNewsAdapter
import com.example.cryptocurrency.databinding.FragmentNewsBinding
import com.example.cryptocurrency.network_detector.NetworkDetector
import com.example.cryptocurrency.utils.navigateWithBundle
import com.example.cryptocurrency.view_model.GeckoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var cryptoNewsAdapter: CryptoNewsAdapter
    private val networkDetector  by lazy { context?.let { NetworkDetector(it) } }
    private val viewModel: GeckoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_news,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newsCircular.isVisible = true
        setupRecyclerView()
        networkDetector?.observe(viewLifecycleOwner){ isConnected ->
            if (isConnected){
                lifecycleScope.launch {
                    viewModel.cryptoNewsLiveData.observe(viewLifecycleOwner){ news ->
                        cryptoNewsAdapter.differ.submitList(news)
                        binding.newsCircular.isVisible = false
                    }
                }
                viewModel.getCryptoNews()
                binding.noConnection.isVisible = false
            }else{
                setupRecyclerView()
                binding.noConnection.isVisible = true
            }
        }

        cryptoNewsAdapter.onItemClick = {
            navigateWithBundle(R.id.nav_news_details, bundleOf("newsUrl" to it.url))
        }

    }

    private fun setupRecyclerView() {
        binding.recView.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            cryptoNewsAdapter = CryptoNewsAdapter()
            adapter = cryptoNewsAdapter
        }
    }
}