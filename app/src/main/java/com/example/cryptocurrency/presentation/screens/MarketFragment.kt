package com.example.cryptocurrency.presentation.screens

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
import com.example.cryptocurrency.domain.model.CryptoDetails
import com.example.cryptocurrency.presentation.view_models.CryptoViewModel
import com.example.cryptocurrency.utils.CRYPTO
import com.example.cryptocurrency.utils.backgroundRes
import com.example.cryptocurrency.utils.collectLatest
import com.example.cryptocurrency.utils.navigateTo
import com.example.cryptocurrency.utils.setEdtBackgroundColor
import com.example.cryptocurrency.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarketFragment : Fragment() {
    private lateinit var binding: FragmentMarketBinding
    private lateinit var marketAdapter: TopGainersAdapter
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
        observeViewModel()
        setupMarketRec()
//        binding.noConnection.isVisible = false
//        networkDetector?.observe(viewLifecycleOwner) { isConnected ->
//            if (isConnected) {
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
            observeViewModel()
            binding.refresher.isRefreshing = false
        }

    }

    private fun observeViewModel(){
            marketViewModel.getCryptos(1,500, CRYPTO.MARKET_LIST.type)
            marketViewModel.market.collectLatest(viewLifecycleOwner){
                binding.circular.isVisible = it.loading
                when{
                    !it.cryptos.isNullOrEmpty() -> {
                        marketViewModel.marketList.value = it.cryptos
                    }
                    it.error.isNotEmpty() -> showToast(it.error)
                }
            }
    }

    private fun setupMarketRec() {
        binding.recView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            marketAdapter = TopGainersAdapter { cryptoDetails ->
                marketViewModel.cryptoDetails.value = cryptoDetails
                navigateTo(R.id.action_market_to_chart_details)
            }
            adapter = marketAdapter
        }
        marketViewModel.marketList.collectLatest(viewLifecycleOwner){
            if (!it.isNullOrEmpty()){
                marketAdapter.differ.submitList(it.sortedBy { cryptoDetails ->
                    cryptoDetails?.cmcRank
                })
                it.let(::searchOnMarket)
            }
        }
    }

    private fun searchOnMarket(marketList: List<CryptoDetails?>) {
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