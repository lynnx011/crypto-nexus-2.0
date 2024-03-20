package com.example.cryptocurrency.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cryptocurrency.R
import com.example.cryptocurrency.adapter.BlockSpanAdapter
import com.example.cryptocurrency.databinding.FragmentNftsBinding
import com.example.cryptocurrency.network_detector.NetworkDetector
import com.example.cryptocurrency.utils.navigateTo
import com.example.cryptocurrency.view_model.GeckoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NftsFragment : Fragment() {
    private lateinit var binding: FragmentNftsBinding
    private lateinit var blockSpanAdapter: BlockSpanAdapter
    private val viewModel: GeckoViewModel by activityViewModels()
    private val networkDetector by lazy { context?.let { NetworkDetector(it) } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nfts, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nftCircular.isVisible = true
        setupRecyclerView()
        viewModel.getBlockSpanNft()
        networkDetector?.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                viewModel.blockSpanNftLiveData.observe(viewLifecycleOwner) { result ->
                    blockSpanAdapter.differ.submitList(result)
                    binding.nftCircular.isVisible = false
                }
                binding.noConnection.isVisible = false
                viewModel.getBlockSpanNft()
            } else {
                setupRecyclerView()
                binding.noConnection.isVisible = true
            }
        }

    }

    private fun setupRecyclerView() {
        binding.recView.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            blockSpanAdapter = BlockSpanAdapter { result, stats ->
                viewModel.nftResult.value = result
                viewModel.nftStats.value = stats
                navigateTo(R.id.nav_block_span_details)

            }
            adapter = blockSpanAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.blockSpanNftLiveData.value = null
    }

}