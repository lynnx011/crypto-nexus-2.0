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
import androidx.fragment.app.viewModels
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
    private val marketViewModel: CryptoViewModel by viewModels()

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
        postDetail()

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
            marketAdapter = TopGainersAdapter()
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

    private fun postDetail() {
        marketAdapter.onItemClick = { crypto ->
            val bundle = Bundle()
            val quote = crypto.quote.USD
            bundle.apply {
                putString("symbol", crypto.symbol)
                putString("id", crypto.id.toString())
                putString("name", crypto.name)
                putString("price", quote.price.toString())
                putString("change24h", quote.percent_change_24h.toString())
                putString("change7d", quote.percent_change_7d.toString())
                putString("change30d", quote.percent_change_30d.toString())
                putString("change60d", quote.percent_change_60d.toString())
                putString("change90d", quote.percent_change_90d.toString())
                putString("rank", crypto.cmc_rank.toString())
                putString("marketCap", quote.market_cap.toString())
                putString("maxSupply", crypto.max_supply.toString())
                putString("cSupply", crypto.circulating_supply.toString())
                putString("totalSupply", crypto.total_supply.toString())
                putString("marketPair", crypto.num_market_pairs.toString())
                putString("dilutedMarket", quote.fully_diluted_market_cap.toString())
                putString("dominanceMarket", quote.market_cap_dominance.toString())
                putString("volume24h", quote.volume_24h.toString())
            }
            findNavController().navigate(R.id.cryptoChartDetailFragment, bundle)
        }
    }

}