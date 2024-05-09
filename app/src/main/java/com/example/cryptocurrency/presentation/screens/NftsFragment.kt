package com.example.cryptocurrency.presentation.screens

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
import com.example.cryptocurrency.presentation.view_models.BlockSpanViewModel
import com.example.cryptocurrency.presentation.view_models.NftViewModel
import com.example.cryptocurrency.utils.collectLatest
import com.example.cryptocurrency.utils.navigateTo
import com.example.cryptocurrency.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NftsFragment : Fragment() {
    private lateinit var binding: FragmentNftsBinding
    private lateinit var blockSpanAdapter: BlockSpanAdapter
    private val blockSpanViewModel: BlockSpanViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nfts, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setupBlockSpanRec()

    }

    private fun observeViewModel() {
            blockSpanViewModel.getBlockSpanNft()
            blockSpanViewModel.blockSpan.collectLatest(viewLifecycleOwner) {
                binding.circular.isVisible = it.loading
                when {
                    it.loading -> {}
                    it.blockSpan != null -> blockSpanViewModel.blockSpanNft.value = it.blockSpan
                    it.error.isNotEmpty() -> showToast(it.error)
                }
            }
    }

    private fun setupBlockSpanRec() {
        binding.recView.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            blockSpanAdapter = BlockSpanAdapter { result, stats ->
                blockSpanViewModel.blockSpanResult.value = result
                blockSpanViewModel.blockSpanStats.value = stats
                navigateTo(R.id.nav_block_span_details)

            }
            adapter = blockSpanAdapter
            blockSpanViewModel.blockSpanNft.collectLatest(viewLifecycleOwner) {
                if (!it?.results.isNullOrEmpty()) {
                    blockSpanAdapter.differ.submitList(it?.results)
                }
            }
        }

    }


}