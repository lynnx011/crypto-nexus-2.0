package com.example.cryptocurrency.fragments
import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.FragmentCryptoChartDetailBinding
import com.example.cryptocurrency.network_detector.NetworkDetector
import com.example.cryptocurrency.view_model.CryptoViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptoChartDetailFragment : Fragment() {
    private lateinit var binding: FragmentCryptoChartDetailBinding
    private lateinit var webView: WebView
    private lateinit var handler: Handler
    private var dialog: BottomSheetDialog? = null
    private val viewModel: CryptoViewModel by activityViewModels()
    private var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>? = null
    private val networkDetector by lazy { context?.let { NetworkDetector(it) } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_crypto_chart_detail,
            container,
            false
        )
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.favBtn.setOnClickListener {
//            setupDetailsBtmSheet()
//        }
//        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
//        when(bottomSheetBehavior!!.state){
//            BottomSheetBehavior.STATE_HIDDEN -> {
//                binding.btmSheetIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.dollar))
//            }
//            BottomSheetBehavior.STATE_COLLAPSED -> {
//                binding.btmSheetIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ape1))
//            }
//            BottomSheetBehavior.STATE_EXPANDED -> {
//                binding.btmSheetIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.analytics))
//            }
//        }

        handler = Handler(Looper.getMainLooper())
        val args = arguments
        val cId = viewModel.cryptoDetails.value?.id
        val cSymbol = viewModel.cryptoDetails.value?.symbol
        val cPrice = viewModel.cryptoDetails.value?.quote?.USD?.price
        val cChange24 = viewModel.cryptoDetails.value?.quote?.USD?.percent_change_24h
        val change7d = viewModel.cryptoDetails.value?.quote?.USD?.percent_change_7d
        val change30d = viewModel.cryptoDetails.value?.quote?.USD?.percent_change_30d
        val change60d = viewModel.cryptoDetails.value?.quote?.USD?.percent_change_60d
        val change90d = viewModel.cryptoDetails.value?.quote?.USD?.percent_change_90d
        val cmcRank = viewModel.cryptoDetails.value?.cmc_rank
        val marketCap = viewModel.cryptoDetails.value?.quote?.USD?.market_cap
        val maxSupply = viewModel.cryptoDetails.value?.max_supply
        val circulatingSupply = viewModel.cryptoDetails.value?.circulating_supply
        val totalSupply = viewModel.cryptoDetails.value?.total_supply
        val marketPair = viewModel.cryptoDetails.value?.num_market_pairs
        val dilutedMarket = viewModel.cryptoDetails.value?.quote?.USD?.fully_diluted_market_cap
        val marketDominance = viewModel.cryptoDetails.value?.quote?.USD?.market_cap_dominance
        val volume24h = viewModel.cryptoDetails.value?.quote?.USD?.volume_24h

        if (cId != null && cSymbol != null && cPrice != null && cChange24 != null) {

            cryptoDetailsSetup(cId.toInt(), cSymbol, cPrice, cChange24)

            networkDetector?.observe(viewLifecycleOwner) { isConnected ->
                if (isConnected) {
                    loadChartData(cSymbol, "15")
                    binding.webView.isVisible = true
                    binding.noConnection.isVisible = false
                    binding.apply {

                        fun buttonClickListener(view: View, timeInterval: String) {
                            view.setOnClickListener {
                                buttonState(view, min15, hour1, hour4, day1, week1, month1)
                                loadChartData(cSymbol, timeInterval)
                            }
                        }
                        buttonClickListener(min15, "15")
                        buttonClickListener(hour1, "1H")
                        buttonClickListener(hour4, "4H")
                        buttonClickListener(day1, "D")
                        buttonClickListener(week1, "W")
                        buttonClickListener(month1, "M")
                    }
                } else {
                    handler.postDelayed({
                        binding.webView.isVisible = false
                        binding.noConnection.isVisible = true
                    }, 3000)
                }
                binding.min15.isEnabled = false
                binding.min15.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.dark_blue1
                    )
                )

                binding.apply {

                    backKey.setOnClickListener {
                        findNavController().popBackStack()
                    }

//                    detailCard.setBackgroundResource(R.drawable.detail_card)
                }
            }
        } else {
            Toast.makeText(requireContext(), "some data isn't received!", Toast.LENGTH_SHORT).show()
        }

        if (cmcRank != null && marketCap != null && maxSupply != null &&
            circulatingSupply != null && totalSupply != null && marketPair != null
            && change7d != null && change30d != null && change60d != null && change90d != null
            && dilutedMarket != null && marketDominance != null && volume24h != null
        ) {


            val formattedMarketCap = formattedValue(marketCap.toString())
            val formattedCirSupply = formattedValue(circulatingSupply.toString())
            val formattedMax = formattedValue(maxSupply.toString())
            val formattedTotal = formattedValue(totalSupply.toString())
            val formattedDilutedMarket = formattedValue(dilutedMarket.toString())
            val formattedMarketDominance = String.format("%.2f%%", marketDominance.toDouble())
            val formattedVolume24h = formattedValue(volume24h.toString())

            setupCryptoDetails(
                cmcRank.toString(),
                formattedMarketCap,
                formattedMax,
                formattedCirSupply,
                formattedTotal,
                formattedDilutedMarket,
                formattedMarketDominance,
                formattedVolume24h,
                change7d.toString(),
                change30d.toString(),
                change60d.toString(),
                change90d.toString()
            )
        } else {
            Toast.makeText(requireContext(), "some data is not received!", Toast.LENGTH_SHORT)
                .show()
        }


        val handler = Handler(Looper.getMainLooper())
        val colorSequence = arrayOf(Color.RED, Color.GREEN, Color.WHITE, Color.WHITE)
        var colorIndex = 0
        val colorRunnable = object : Runnable {
            override fun run() {
                binding.price.setTextColor(colorSequence[colorIndex])
                colorIndex = (colorIndex + 1) % colorSequence.size
                handler.postDelayed(this, 3000)
            }
        }
        handler.postDelayed(colorRunnable, 100)
    }

    @SuppressLint("SetTextI18n")
    private fun setupCryptoDetails(
        cmcRank: String,
        marketCap: String,
        maxSupply: String,
        circulatingSupply: String,
        totalSupply: String,
        dilutedMarket: String,
        marketDominance: String,
        volume24h: String,
        change7d: String,
        change30d: String,
        change60d: String,
        change90d: String
    ) {
        binding.apply {
            rank.text = "#$cmcRank"
            mCap.text = marketCap
            mSupply.text = maxSupply
            cSupply.text = circulatingSupply
            tSupply.text = totalSupply
            binding.dilutedMarket.text = dilutedMarket
            binding.marketDominance.text = marketDominance
            mVolume.text = volume24h
            change7.text = change7d
            change30.text = change30d
            change60.text = change60d
            change90.text = change90d


            caretState(change7d.toDouble(), change7, caret7)
            caretState(change30d.toDouble(), change30, caret30)
            caretState(change60d.toDouble(), change60, caret60)
            caretState(change90d.toDouble(), change90, caret90)
        }

    }

//    private fun setupDetailsBtmSheet(){
//        val btmSheetView = layoutInflater.inflate(R.layout.custom_details_btm_sheet,null)
//        dialog = BottomSheetDialog(requireContext(),R.style.Theme_CryptoCurrency)
//        dialog!!.setContentView(btmSheetView)
//        dialog!!.show()
//    }

    private fun cryptoDetailsSetup(
        tokenId: Int,
        tokenSymbol: String,
        tokenPrice: Double,
        tokenChange24h: Double
    ) {
        binding.apply {
            symbol.text = tokenSymbol
            price.text = String.format("$%.6f", tokenPrice)
            percent24h.text = tokenChange24h.toString()

            Glide.with(requireContext())
                .load("https://s2.coinmarketcap.com/static/img/coins/64x64/${tokenId}.png")
                .thumbnail(Glide.with(requireContext()).load(R.drawable.loading3))
                .into(logo)

            caretState(tokenChange24h, percent24h, caret)
        }
    }

    private fun buttonState(view: View, vararg buttons: View) {
        for (button in buttons) {
            button.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    if (view == button) R.color.dark_blue1 else R.color.grey3
                )
            )
            button.isEnabled = button != view
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadChartData(tokenSymbol: String, timeInterval: String) {
        val chartUrl =
            "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=${tokenSymbol}USD&interval=$timeInterval&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=%5B%5D&hideideas=1&theme=Dark&style=1&timezone=Asia%2FYangon&studies_overrides=%7B%7D&overrides=%7B%7D&enabled_features=%5B%5D&disabled_features=%5B%5D&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=$tokenSymbol"
        webView = binding.webView
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.loadUrl(chartUrl)
    }

    private fun caretState(percent: Double, value: TextView, caret: ImageView) {
        if (percent > 0) {
            value.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            value.text = String.format("%.6f%%", percent)
            caret.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.caret_up
                )
            )
        }
        if (percent < 0) {
            val subPercent = kotlin.math.abs(percent)
            val formattedPercent = String.format("%.6f%%", subPercent)
            value.setTextColor(ContextCompat.getColor(requireContext(), R.color.chili))
            value.text = formattedPercent.substring(0)
            caret.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.caret_down
                )
            )
        }

    }

    private fun formattedValue(value: String): String {
        val marketValue = value.toDouble()
        val suffix = if (marketValue >= 1_000_000_000) "Bn" else "M"
        val divisor = if (marketValue >= 1_000_000_000) 1_000_000_000 else 1_000_000
        return String.format("%.3f", marketValue / divisor) + " " + suffix
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cryptoDetails.value = null
    }
}