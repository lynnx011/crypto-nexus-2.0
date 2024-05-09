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
import com.example.cryptocurrency.adapter.TransactionAdapter
import com.example.cryptocurrency.databinding.FragmentAddTransactionBinding
import com.example.cryptocurrency.network_detector.NetworkDetector
import com.example.cryptocurrency.presentation.view_models.CryptoViewModel
import com.example.cryptocurrency.utils.backgroundRes
import com.example.cryptocurrency.utils.navigateTo
import com.example.cryptocurrency.utils.popBack
import com.example.cryptocurrency.utils.setEdtBackgroundColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTransactionFragment : Fragment() {
    private lateinit var binding: FragmentAddTransactionBinding
    private lateinit var transAdapter: TransactionAdapter
    private val networkDetector by lazy { context?.let { NetworkDetector(it) } }
    private val viewModel: CryptoViewModel by activityViewModels()
//    private val portfolioViewModel: PortfolioViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_transaction, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.getMarketData()
//        binding.addTransCircular.isVisible = true
//        binding.searchView.setEdtBackgroundColor(requireContext(),R.color.grey3)
//        binding.searchCard.backgroundRes(R.drawable.search_bar_background)
//        binding.noConnection.isVisible = false
//        networkDetector?.observe(viewLifecycleOwner) { isConnected ->
//            if (isConnected) {
//                setupRecyclerView()
//                viewModel.marketLiveData.observe(viewLifecycleOwner) { cryptos ->
//                    transAdapter.differ.submitList(cryptos)
//                    filterItem(cryptos)
//                    binding.addTransCircular.isVisible = false
//                }
//            } else {
//                portfolioViewModel.getRoomCryptos().observe(viewLifecycleOwner) { cryptos ->
//                    transAdapter.differ.submitList(cryptos)
//                    filterItem(cryptos)
//                    binding.addTransCircular.isVisible = false
//                }
//                setupRecyclerView()
//
//            }
//        }
//
//        binding.backKey.setOnClickListener {
//            popBack()
//        }
    }

    private fun setupRecyclerView() {
        binding.recView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            transAdapter = TransactionAdapter { details ->
                viewModel.cryptoDetails.value = details
                navigateTo(R.id.action_add_trans_to_amount_trans)
            }
            adapter = transAdapter
        }
    }

//    private fun filterItem(list: List<CryptoDetails>) {
//        binding.searchView.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun onTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if (query != null) {
//                    transAdapter.filter.filter(query)
//                    transAdapter.updateFilteredList(list)
//
//                }
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//
//            }
//
//        })
//    }

}