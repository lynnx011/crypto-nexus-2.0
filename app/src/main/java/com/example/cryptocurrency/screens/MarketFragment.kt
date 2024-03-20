package com.example.cryptocurrency.screens

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrency.R
import com.example.cryptocurrency.adapter.TopGainersAdapter
import com.example.cryptocurrency.databinding.FragmentMarketBinding
import com.example.cryptocurrency.model.CryptoDetails
import com.example.cryptocurrency.network_detector.NetworkDetector
import com.example.cryptocurrency.utils.backgroundRes
import com.example.cryptocurrency.utils.navigateTo
import com.example.cryptocurrency.utils.setEdtBackgroundColor
import com.example.cryptocurrency.view_model.CryptoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarketFragment : Fragment() {
    private lateinit var binding: FragmentMarketBinding
    private lateinit var marketAdapter: TopGainersAdapter
    private val networkDetector by lazy { context?.let { NetworkDetector(it) } }
    private val marketViewModel: CryptoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_market, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var marketList = listOf<CryptoDetails>()
        binding.marketCircular.isVisible = true
        setupMarketRecyclerview()
//        binding.noConnection.isVisible = false
//        networkDetector?.observe(viewLifecycleOwner) { isConnected ->
//            if (isConnected) {
                marketViewModel.getMarketData()
                marketViewModel.marketLiveData.observe(viewLifecycleOwner) { cryptos ->
                    marketAdapter.differ.submitList(cryptos)
                    binding.marketCircular.isVisible = false
                    marketList = cryptos
                    searchOnMarket(marketList)
//                }
//
//            }
//            else {
//                marketViewModel.getRoomCryptos().observe(viewLifecycleOwner) { cryptos ->
//                    marketAdapter.differ.submitList(cryptos.sortedBy { it.cmc_rank })
//                    binding.marketCircular.isVisible = false
//                    searchOnMarket(marketList)
//                }
//            }

        }

        binding.searchView.apply {
            setEdtBackgroundColor(
                requireContext(),
                R.color.grey3
            )
            this.setOnClickListener {
               this.requestFocus()
            }
        }

        binding.searchCard.backgroundRes(R.drawable.search_bar_background)

        binding.refresher.setOnRefreshListener {
            marketViewModel.getMarketData()
            binding.refresher.isRefreshing = false
        }

    }

    private fun setupMarketRecyclerview() {
        binding.recView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            marketAdapter = TopGainersAdapter { cryptoDetails ->
                marketViewModel.cryptoDetails.value = cryptoDetails
                navigateTo(R.id.action_market_to_chart_details)
            }
            adapter = marketAdapter
        }
    }

    private fun searchOnMarket(marketList: List<CryptoDetails>) {
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (query != null) {
                    marketAdapter.filter.filter(query)
                    marketAdapter.updateFilteredItem(marketList)

                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

}