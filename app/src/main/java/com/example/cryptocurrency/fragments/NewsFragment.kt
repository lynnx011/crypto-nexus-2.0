package com.example.cryptocurrency.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrency.R
import com.example.cryptocurrency.adapter.CryptoNewsAdapter
import com.example.cryptocurrency.databinding.FragmentNewsBinding
import com.example.cryptocurrency.network_detector.NetworkDetector
import com.example.cryptocurrency.view_model.CryptoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var cryptoNewsAdapter: CryptoNewsAdapter
    private val networkDetector  by lazy { context?.let { NetworkDetector(it) } }
    private val cryptoNewsViewModel: CryptoViewModel by viewModels()

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
                    cryptoNewsViewModel.cryptoNewsLiveData.observe(viewLifecycleOwner){ news ->
                        cryptoNewsAdapter.differ.submitList(news)
                        binding.newsCircular.isVisible = false
                    }
                }
                cryptoNewsViewModel.getCryptoNews()
                binding.noConnection.isVisible = false
            }else{
                setupRecyclerView()
                binding.noConnection.isVisible = true
            }
        }

        cryptoNewsAdapter.onItemClick = {
            findNavController().navigate(R.id.newsDetailsFragment, bundleOf("newsUrl" to it.url))
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