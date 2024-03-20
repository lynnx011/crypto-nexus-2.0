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
import com.example.cryptocurrency.databinding.FragmentTopLosersBinding
import com.example.cryptocurrency.network_detector.NetworkDetector
import com.example.cryptocurrency.utils.navigateTo
import com.example.cryptocurrency.view_model.CryptoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopLosersFragment : Fragment() {
    private lateinit var binding: FragmentTopLosersBinding
    private lateinit var losersAdapter: TopGainersAdapter
    private val networkDetector by lazy { context?.let { NetworkDetector(it) } }
    private val losersViewModel: CryptoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_losers, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loserCircular.isVisible = true
        losersRecyclerView()

        networkDetector?.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                losersViewModel.topGainersLosersLiveData.observe(viewLifecycleOwner) { cryptos ->
                    if (cryptos != null) {
                        val topLosers = cryptos.sortedBy {
                            it.quote.USD.percent_change_24h
                        }.take(100)
                        losersAdapter.differ.submitList(topLosers)
                        binding.loserCircular.isVisible = false
                    }
                }
                losersViewModel.getTopGainersLosers()
            }
        }
    }

    private fun losersRecyclerView() {
        binding.recView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            losersAdapter = TopGainersAdapter { cryptoDetails ->
                losersViewModel.cryptoDetails.value = cryptoDetails
                if (findNavController().currentDestination?.id == R.id.nav_home) {
                    navigateTo(
                        R.id.action_home_to_chart_details
                    )
                }
            }
            adapter = losersAdapter
        }
    }

}