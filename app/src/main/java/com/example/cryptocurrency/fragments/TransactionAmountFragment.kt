package com.example.cryptocurrency.fragments
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.FragmentTransactionAmountBinding
import com.example.cryptocurrency.model.transaction.TransactionModel
import com.example.cryptocurrency.view_model.CryptoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionAmountFragment : Fragment() {
    private lateinit var binding: FragmentTransactionAmountBinding
    private val transactionViewModel: CryptoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_transaction_amount,container,false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments

        val cryptoName = args?.getString("name")
        val cryptoSymbol = args?.getString("symbol")
        val cryptoId = args?.getString("id")
        val price = args?.getString("price")
        val percent24h = args?.getString("change24h")
        val cryptoPrice = price?.toDouble()


        if (cryptoName != null && cryptoId != null && cryptoSymbol != null && cryptoPrice != null){
            binding.apply {
                symbol.text = cryptoSymbol.toString()
                name.text = cryptoName.toString()

                Glide.with(requireContext())
                    .load("https://s2.coinmarketcap.com/static/img/coins/64x64/$cryptoId.png")
                    .into(logo)

                perCoin.text = String.format("$%.2f", cryptoPrice) + " per coin"

                symbol1.text = cryptoSymbol.toString()
            }

        }
        binding.transBtn.setOnClickListener {
            transactionProcess(cryptoId.toString(), cryptoName.toString(), cryptoSymbol.toString(), cryptoPrice!!.toDouble(), binding.total.text.toString().toDouble(), binding.etAmount.text.toString().toDouble(), percent24h.toString().toDouble())
        }

        binding.etAmount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }


            override fun afterTextChanged(query: Editable?) {
                if (query != null) {
                    if (query.isNotEmpty()) {
                        try {
                            val parsedAmount = Integer.parseInt(query.toString())
                                transactionViewModel.getCryptoConversion(parsedAmount, cryptoSymbol!!, "USD")
                            transactionViewModel.conversionLiveData.observe(viewLifecycleOwner) { convertedData ->
                                if (convertedData != null) {
                                    val data = convertedData.data[0].quote.USD.price
                                    binding.total.text = data
                                }else{
                                    binding.total.text = "0"
                                }
                            }
                        } catch (e: NumberFormatException) {
                            binding.total.text = "0"
                        }
                    } else {
                        binding.total.text = "0"
                    }
                }
            }

        })

        binding.etAmount.isCursorVisible = false
        binding.backKey.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun transactionProcess(id: String,name: String,symbol: String,currentPrice: Double,amountUsd: Double,tokenAmount: Double,percentChange: Double){
        val transaction = TransactionModel(id = id, name = name, symbol = symbol, current_price = currentPrice, usd_amount = amountUsd, token_amount = tokenAmount, percent_change = percentChange)
        transactionViewModel.insertTransaction(transaction)
        findNavController().navigate(R.id.portfolioFragment)
    }

}