package com.example.cryptocurrency.screens

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.FragmentCryptoChartDetailBinding
import com.example.cryptocurrency.network_detector.NetworkDetector
import com.example.cryptocurrency.utils.cryptoLogoUrl
import com.example.cryptocurrency.utils.loadImage
import com.example.cryptocurrency.utils.popBack
import com.example.cryptocurrency.utils.setBackgroundColor
import com.example.cryptocurrency.utils.setBtnBackground
import com.example.cryptocurrency.utils.setImgDrawable
import com.example.cryptocurrency.utils.setTextColor
import com.example.cryptocurrency.view_model.CryptoViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptoChartDetailFragment : Fragment() {
    private lateinit var binding: FragmentCryptoChartDetailBinding
    private lateinit var webView: WebView
    private lateinit var handler: Handler
    private val viewModel: CryptoViewModel by activityViewModels()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private val networkDetector by lazy { context?.let { NetworkDetector(it) } }
    private var isCollapsed = false

    override fun onStart() {
        super.onStart()
        loadPortfolioItemDetails()
    }

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
        handler = Handler(Looper.getMainLooper())
        viewModel.cryptoDetails.observe(viewLifecycleOwner) { details ->
            val cId = details?.id ?: 0
            val cSymbol = details?.symbol ?: ""
            val cPrice = details?.quote?.USD?.price ?: 0.0
            val cChange24 = details?.quote?.USD?.percent_change_24h ?: 0.0
            val change7d = details?.quote?.USD?.percent_change_7d ?: 0.0
            val change30d = details?.quote?.USD?.percent_change_30d ?: 0.0
            val change60d = details?.quote?.USD?.percent_change_60d ?: 0.0
            val change90d = details?.quote?.USD?.percent_change_90d ?: 0.0
            val cmcRank = details?.cmc_rank ?: 0
            val marketCap = details?.quote?.USD?.market_cap ?: 0.0
//            val maxSupply = details?.max_supply ?: 0
            val circulatingSupply = details?.circulating_supply ?: 0.0
            val totalSupply = details?.total_supply ?: 0.0
            val marketPair = details?.num_market_pairs ?: 0
            val dilutedMarket = details?.quote?.USD?.fully_diluted_market_cap ?: 0.0
            val marketDominance = details?.quote?.USD?.market_cap_dominance ?: 0.0
            val volume24h = details?.quote?.USD?.volume_24h ?: 0.0


            cryptoDetailsSetup(cId, cSymbol, cPrice, cChange24)

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

                binding.min15.apply {
                    isEnabled = false
                    setBtnBackground(requireContext(), R.color.dark_blue1)
                }

            }

            binding.backKey.setOnClickListener {
                popBack()
            }

            val formattedMarketCap = formattedValue(marketCap.toString())
            val formattedCirSupply = formattedValue(circulatingSupply.toString())
//            val formattedMax = formattedValue(maxSupply.toString())
            val formattedTotal = formattedValue(totalSupply.toString())
            val formattedDilutedMarket = formattedValue(dilutedMarket.toString())
            val formattedMarketDominance = String.format("%.2f%%", marketDominance)
            val formattedVolume24h = formattedValue(volume24h.toString())

            setupCryptoDetails(
                cmcRank.toString(),
                formattedMarketCap,
//                formattedMax,
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
//        maxSupply: String,
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
//            mSupply.text = maxSupply
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

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        binding.bottomSheet.setOnClickListener {
            when (isCollapsed) {
                true -> {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    isCollapsed = false
                }

                else -> {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    isCollapsed = true
                }
            }
        }

        bottomSheetDragListener()

    }

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

            logo.loadImage(
                requireContext(),
                cryptoLogoUrl(tokenId),
                R.drawable.loading3
            )

            caretState(tokenChange24h, percent24h, caret)
        }
    }

    private fun buttonState(view: View, vararg buttons: View) {
        for (button in buttons) {
            button.setBackgroundColor(
                requireContext(),
                if (view == button) R.color.dark_blue1 else R.color.grey3
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
            value.setTextColor(requireContext(), R.color.green)
            value.text = String.format("%.2f%%", percent)
            caret.setImgDrawable(
                requireContext(),
                R.drawable.caret_up
            )
        }
        if (percent < 0) {
            val subPercent = kotlin.math.abs(percent)
            val formattedPercent = String.format("%.2f%%", subPercent)
            value.setTextColor(requireContext(), R.color.chili)
            value.text = formattedPercent.substring(0)
            caret.setImgDrawable(
                requireContext(),
                R.drawable.caret_down
            )
        }

    }

    private fun formattedValue(value: String?): String {
        val marketValue = value?.toDouble()
        val suffix = if (marketValue!! >= 1_000_000_000) "Bn" else "M"
        val divisor = if (marketValue >= 1_000_000_000) 1_000_000_000 else 1_000_000
        return String.format("%.2f", marketValue / divisor) + " " + suffix
    }

    private fun loadPortfolioItemDetails() {
        try {
            val portfolioName = arguments?.getString("portfolio_name")
            val portfolioSymbol = arguments?.getString("portfolio_symbol")
            if (portfolioSymbol != null && portfolioName?.isNotEmpty() == true) {
                viewModel.getMarketData()
                viewModel.marketLiveData.observe(viewLifecycleOwner) { details ->
                    if (details != null) {
                        val filtered = details.filter { it.name == portfolioName }
                        for (i in filtered) {
                            viewModel.cryptoDetails.value = i
                            Log.d("portfolio", i.toString())
                        }
                    }
                }

            }
        } catch (e: Exception) {
            Log.d("portfolio_details", e.toString())
        }
    }

    private fun bottomSheetDragListener() {
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        binding.swipeTextLayout.visibility = View.GONE
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.swipeTextLayout.visibility = View.VISIBLE
                    }

                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.swipeTextLayout.visibility = View.GONE
                    }

                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                bottomSheetBehavior.peekHeight = 130
            }

        })
    }

}