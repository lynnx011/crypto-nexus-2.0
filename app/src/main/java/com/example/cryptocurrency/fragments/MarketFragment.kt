package com.example.cryptocurrency.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrency.R
import com.example.cryptocurrency.adapter.TopGainersAdapter
import com.example.cryptocurrency.databinding.FragmentMarketBinding
import com.example.cryptocurrency.model.CryptoDetails
import com.example.cryptocurrency.network_detector.NetworkDetector
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
        binding.noConnection.isVisible = false
        networkDetector?.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                marketViewModel.marketLiveData.observe(viewLifecycleOwner) { cryptos ->
                    marketAdapter.differ.submitList(cryptos)
                    binding.marketCircular.isVisible = false
                    marketList = cryptos
                    searchOnMarket(marketList)
                }
                marketViewModel.getMarketData()

            } else {
                marketViewModel.getRoomCryptos().observe(viewLifecycleOwner) { cryptos ->
                    marketAdapter.differ.submitList(cryptos)
                    binding.marketCircular.isVisible = false
                    searchOnMarket(marketList)
                }
            }

        }

        binding.searchView.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.grey3
            )
        )
        binding.searchCard.setBackgroundResource(R.drawable.search_bar_background)

        binding.searchView.setOnClickListener {
            binding.searchView.requestFocus()
        }

    }

    private fun setupMarketRecyclerview() {
        binding.recView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            marketAdapter = TopGainersAdapter { cryptoDetails ->
                marketViewModel.cryptoDetails.value = cryptoDetails
                findNavController().navigate(R.id.action_marketFragment_to_cryptoChartDetailFragment)
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