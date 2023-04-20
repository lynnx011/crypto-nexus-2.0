package com.example.cryptocurrency.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrency.R
import com.example.cryptocurrency.adapter.TopGainersAdapter
import com.example.cryptocurrency.databinding.FragmentTopLosersBinding
import com.example.cryptocurrency.network_detector.NetworkDetector
import com.example.cryptocurrency.view_model.CryptoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopLosersFragment : Fragment() {
    private lateinit var binding: FragmentTopLosersBinding
    private lateinit var losersAdapter: TopGainersAdapter
    private val networkDetector  by lazy { context?.let { NetworkDetector(it) } }
    private val losersViewModel: CryptoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_top_losers,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loserCircular.isVisible = true
        losersRecyclerView()

        networkDetector?.observe(viewLifecycleOwner){ isConnected ->
            if (isConnected){
                losersViewModel.topGainersLosersLiveData.observe(viewLifecycleOwner){ cryptos ->
                    if (cryptos != null){
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

        losersAdapter.onItemClick = { crypto ->
            val bundle = Bundle()
            val quote = crypto.quote.USD
            bundle.apply {
                putString("symbol",crypto.symbol)
                putString("id",crypto.id.toString())
                putString("name",crypto.name)
                putString("price", quote.price.toString())
                putString("change24h",quote.percent_change_24h.toString())
                putString("change7d",quote.percent_change_7d.toString())
                putString("change30d",quote.percent_change_30d.toString())
                putString("change60d",quote.percent_change_60d.toString())
                putString("change90d",quote.percent_change_90d.toString())
                putString("rank",crypto.cmc_rank.toString())
                putString("marketCap",quote.market_cap.toString())
                putString("maxSupply",crypto.max_supply.toString())
                putString("cSupply",crypto.circulating_supply.toString())
                putString("totalSupply",crypto.total_supply.toString())
                putString("marketPair",crypto.num_market_pairs.toString())
                putString("dilutedMarket",quote.fully_diluted_market_cap.toString())
                putString("dominanceMarket",quote.market_cap_dominance.toString())
                putString("volume24h",quote.volume_24h.toString())
            }
            findNavController().navigate(R.id.cryptoChartDetailFragment,bundle)
        }
    }

    private fun losersRecyclerView(){
        binding.recView.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            losersAdapter = TopGainersAdapter()
            adapter = losersAdapter
        }
    }

}