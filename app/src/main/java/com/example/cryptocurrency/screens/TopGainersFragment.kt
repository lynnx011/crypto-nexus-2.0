package com.example.cryptocurrency.screens

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
import com.example.cryptocurrency.network_detector.NetworkDetector
import com.example.cryptocurrency.utils.navigateTo
import com.example.cryptocurrency.view_model.CryptoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopGainersFragment : Fragment() {
    private lateinit var binding: FragmentTopGainersBinding
    private lateinit var gainersAdapter: TopGainersAdapter
    private val gainerViewModel: CryptoViewModel by activityViewModels()
    private val networkDetector by lazy { context?.let { NetworkDetector(it) } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_gainers, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gainerCircular.isVisible = true

        gainersRecyclerView()

        networkDetector?.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                gainerViewModel.topGainersLosersLiveData.observe(viewLifecycleOwner) { cryptos ->
                    if (cryptos != null) {
                        val topGainers = cryptos.sortedByDescending {
                            it.quote.USD.percent_change_24h
                        }.take(100)
                        gainersAdapter.differ.submitList(topGainers)
                        binding.gainerCircular.isVisible = false
                    }
                }
                gainerViewModel.getTopGainersLosers()
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
    }


}