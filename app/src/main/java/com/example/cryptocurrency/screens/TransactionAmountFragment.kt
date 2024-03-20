package com.example.cryptocurrency.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.FragmentTransactionAmountBinding
import com.example.cryptocurrency.model.transaction.TransactionModel
import com.example.cryptocurrency.utils.loadImg
import com.example.cryptocurrency.utils.navigateTo
import com.example.cryptocurrency.utils.popBack
import com.example.cryptocurrency.utils.setBackgroundColor
import com.example.cryptocurrency.view_model.CryptoViewModel
import com.example.cryptocurrency.view_model.PortfolioViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionAmountFragment : Fragment() {
    private lateinit var binding: FragmentTransactionAmountBinding
    private val portfolioViewModel: PortfolioViewModel by activityViewModels()
    private val viewModel: CryptoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_transaction_amount,
            container,
            false
        )
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        val cryptoName = viewModel.cryptoDetails.value?.name
        val cryptoSymbol = viewModel.cryptoDetails.value?.symbol
        val cryptoId = viewModel.cryptoDetails.value?.id
        val price = viewModel.cryptoDetails.value?.quote?.USD?.price
        val percent24h = viewModel.cryptoDetails.value?.quote?.USD?.percent_change_24h

        binding.apply {
            symbol.text = cryptoSymbol.orEmpty()
            name.text = cryptoName.orEmpty()

            logo.loadImg(
                requireContext(),
                "https://s2.coinmarketcap.com/static/img/coins/64x64/$cryptoId.png"
            )

            perCoin.text = String.format("$%.2f", price ?: 0.0) + " per coin"

            symbol1.text = cryptoSymbol.orEmpty()
        }

        binding.transBtn.setOnClickListener {
            try {
                transactionProcess(
                    cryptoId.toString(),
                    cryptoName.toString(),
                    cryptoSymbol.toString(),
                    price ?: 0.0,
                    portfolioViewModel.convertResult.value?.toString()?.toDouble() ?: 0.0,
                    portfolioViewModel.convertAmount.value.toString().toDouble(),
                    percent24h.toString().toDouble()
                )
            } catch (e: Exception) {
                Log.d("insert-transaction", e.toString())
            }
            portfolioViewModel.convertAmount.value = ""
        }

        portfolioViewModel.convertAmount.observe(viewLifecycleOwner) {
            portfolioViewModel.transBtnValid()
            if (it != "") portfolioViewModel.getCryptoConversion(cryptoSymbol!!)
            else portfolioViewModel.convertResult.value = "0.0"
        }

        portfolioViewModel.convertResult.observe(viewLifecycleOwner) {
            portfolioViewModel.transBtnValid()
            if (it.toDouble() == 0.0 || portfolioViewModel.convertAmount.value.isNullOrEmpty()) binding.transBtn.setBackgroundColor(
                requireContext(),
                R.color.grey4
            )
            else binding.transBtn.setBackgroundColor(requireContext(), R.color.tangerine1)
        }

        binding.etAmount.isCursorVisible = false
        binding.titleRow.setOnClickListener {
            navigateTo(R.id.action_amount_trans_to_chart_details)
        }
        binding.backKey.setOnClickListener {
            popBack()
        }
    }

    private fun initViewModel() {
        binding.viewModel = portfolioViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun transactionProcess(
        id: String,
        name: String,
        symbol: String,
        currentPrice: Double,
        amountUsd: Double,
        tokenAmount: Double,
        percentChange: Double
    ) {
        val transaction = TransactionModel(
            id = id,
            name = name,
            symbol = symbol,
            current_price = currentPrice,
            usd_amount = amountUsd,
            token_amount = tokenAmount,
            percent_change = percentChange
        )
        portfolioViewModel.insertTransaction(transaction)
        popBack()
        navigateTo(R.id.nav_portfolio)
    }

}