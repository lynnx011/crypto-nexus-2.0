package com.example.cryptocurrency.fragments

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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionAmountFragment : Fragment() {
    private lateinit var binding: FragmentTransactionAmountBinding
    private val transactionViewModel: CryptoViewModel by activityViewModels()

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

        val cryptoName = transactionViewModel.cryptoDetails.value?.name
        val cryptoSymbol = transactionViewModel.cryptoDetails.value?.symbol
        val cryptoId = transactionViewModel.cryptoDetails.value?.id
        val price = transactionViewModel.cryptoDetails.value?.quote?.USD?.price
        val percent24h = transactionViewModel.cryptoDetails.value?.quote?.USD?.percent_change_24h

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
                    transactionViewModel.convertResult.value?.toString()?.toDouble() ?: 0.0,
                    transactionViewModel.convertAmount.value.toString().toDouble(),
                    percent24h.toString().toDouble()
                )
            } catch (e: Exception) {
                Log.d("insert-transaction", e.toString())
            }
            transactionViewModel.convertAmount.value = ""
        }

        transactionViewModel.convertAmount.observe(viewLifecycleOwner) {
            transactionViewModel.transBtnValid()
            if (it != "") transactionViewModel.getCryptoConversion(cryptoSymbol!!)
            else transactionViewModel.convertResult.value = "0.0"
        }

        transactionViewModel.convertResult.observe(viewLifecycleOwner) {
            transactionViewModel.transBtnValid()
            if (it.toDouble() == 0.0 || transactionViewModel.convertAmount.value.isNullOrEmpty()) binding.transBtn.setBackgroundColor(
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
        binding.viewModel = transactionViewModel
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
        transactionViewModel.insertTransaction(transaction)
        popBack()
        navigateTo(R.id.nav_portfolio)
    }

}