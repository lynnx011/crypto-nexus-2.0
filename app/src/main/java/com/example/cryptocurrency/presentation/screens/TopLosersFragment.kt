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
import com.example.cryptocurrency.databinding.FragmentTopLosersBinding
import com.example.cryptocurrency.presentation.view_models.CryptoViewModel
import com.example.cryptocurrency.utils.CRYPTO
import com.example.cryptocurrency.utils.collectLatest
import com.example.cryptocurrency.utils.navigateTo
import com.example.cryptocurrency.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopLosersFragment : Fragment() {
    private lateinit var binding: FragmentTopLosersBinding
    private lateinit var losersAdapter: TopGainersAdapter
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

//        observeViewModel()

        setupTopLosersRec()
    }


//    private fun observeViewModel(){
//        if (losersViewModel.topLosers.value.isNullOrEmpty()){
//            losersViewModel.getCryptos(1,700, CRYPTO.LOSER.type)
//            losersViewModel.losers.collectLatest(viewLifecycleOwner){
//                when {
//                    it.loading -> binding.circular.isVisible = true
//                    it.cryptos.isNotEmpty() -> losersViewModel.topLosers.value = it.cryptos ?: emptyList()
//                    it.error.isNotEmpty() -> showToast(it.error)
//                }
//            }
//        }
//    }

    private fun setupTopLosersRec() {
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
        losersViewModel.topGainers.collectLatest(viewLifecycleOwner){
            if (!it.isNullOrEmpty()){
                losersAdapter.differ.submitList(it.sortedBy { cryptoDetails ->
                    cryptoDetails?.quote?.usd?.percentChange24h
                }.take(100))
            }
        }
    }

}