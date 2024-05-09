package com.example.cryptocurrency.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrency.R
import com.example.cryptocurrency.adapter.TopGainersAdapter
import com.example.cryptocurrency.databinding.FragmentTopGainersBinding
import com.example.cryptocurrency.presentation.view_models.CryptoViewModel
import com.example.cryptocurrency.utils.CRYPTO
import com.example.cryptocurrency.utils.collectLatest
import com.example.cryptocurrency.utils.navigateTo
import com.example.cryptocurrency.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopGainersFragment : Fragment() {
    private lateinit var binding: FragmentTopGainersBinding
    private lateinit var gainersAdapter: TopGainersAdapter
    private val gainerViewModel: CryptoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_gainers, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gainersRecyclerView()

        observeViewModel()

    }

    private fun observeViewModel(){
        if (gainerViewModel.topGainers.value.isNullOrEmpty()){
            gainerViewModel.getCryptos(1,300, CRYPTO.GAINER.type)
            gainerViewModel.gainers.collectLatest(viewLifecycleOwner){
                binding.circular.isVisible = it.loading
                when {
                    !it.cryptos.isNullOrEmpty() -> gainerViewModel.topGainers.value = it.cryptos
                    it.error.isNotEmpty() -> showToast(it.error)
                }
            }
        }
    }

    private fun gainersRecyclerView() {
        binding.recView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            gainersAdapter = TopGainersAdapter { cryptoDetails ->
                gainerViewModel.cryptoDetails.value = cryptoDetails
                if (findNavController().currentDestination?.id == R.id.nav_home) {
                    navigateTo(
                        R.id.action_home_to_chart_details
                    )
                }
            }
            adapter = gainersAdapter
        }
        gainerViewModel.topGainers.collectLatest(viewLifecycleOwner){
            if (!it.isNullOrEmpty()){
                gainersAdapter.differ.submitList(it.sortedByDescending { cryptoDetails ->
                    cryptoDetails?.quote?.usd?.percentChange24h
                }.take(50))
            }
        }
    }


}