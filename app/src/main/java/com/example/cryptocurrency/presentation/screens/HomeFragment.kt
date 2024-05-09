package com.example.cryptocurrency.presentation.screens

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.cryptocurrency.R
import com.example.cryptocurrency.adapter.GainLossPagerAdapter
import com.example.cryptocurrency.adapter.TopCryptoAdapter
import com.example.cryptocurrency.databinding.FragmentHomeBinding
import com.example.cryptocurrency.network_detector.NetworkDetector
import com.example.cryptocurrency.presentation.view_models.CryptoViewModel
import com.example.cryptocurrency.utils.CRYPTO
import com.example.cryptocurrency.utils.ImageSliderAdapter
import com.example.cryptocurrency.utils.collectLatest
import com.example.cryptocurrency.utils.navigateTo
import com.example.cryptocurrency.utils.showToast
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var cryptoAdapter: TopCryptoAdapter
    private lateinit var viewPager2: ViewPager2
    private lateinit var viewPager: ViewPager2
    private var handler: Handler = Handler(Looper.getMainLooper())
    private lateinit var tabLayout: TabLayout
    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private lateinit var imageList: ArrayList<Int>
    private lateinit var pagerAdapter: GainLossPagerAdapter
    private val cryptoViewModel: CryptoViewModel by activityViewModels()

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 4000)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setupTopCryptoRec()
        setupImageSlider()
        setupImageSliderTransformer()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.apply {
                    removeCallbacks(runnable)
                    postDelayed(runnable, 4000)
                }
            }
        })

        tabLayout = binding.tabLayout
        viewPager2 = binding.viewPager2
        pagerAdapter = GainLossPagerAdapter(childFragmentManager, lifecycle)

        tabLayout.addTab(tabLayout.newTab().setText("Top Gainers"))
        tabLayout.addTab(tabLayout.newTab().setText("Top Losers"))

        viewPager2.adapter = pagerAdapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager2.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

    }

    private fun observeViewModel(){
            cryptoViewModel.getCryptos(1,100, CRYPTO.TOP_CRYPTO.type)
            cryptoViewModel.top.collectLatest(viewLifecycleOwner){
                binding.circular.isVisible = it.loading
                when{
                    !it.cryptos.isNullOrEmpty() -> {
                        cryptoViewModel.topCryptos.value = it.cryptos
                    }
                    it.error.isNotEmpty() -> showToast(it.error)
                }
            }
    }

    private val runnable = Runnable {
        viewPager.currentItem += 1
    }

    private fun setupTopCryptoRec() {
        binding.recView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            cryptoAdapter = TopCryptoAdapter { crypto ->
                cryptoViewModel.cryptoDetails.value = crypto
                navigateTo(R.id.action_home_to_chart_details)
            }
            adapter = cryptoAdapter
        }
        cryptoViewModel.topCryptos.collectLatest(viewLifecycleOwner){
            if (!it.isNullOrEmpty()){
                cryptoAdapter.differ.submitList(it)
            }
        }
    }

    private fun setupImageSlider() {
        imageList = ArrayList()
        imageList.apply {
            add(R.drawable.ai_cloud_with_robot_face)
            add(R.drawable.cryptocurrency_bitcoin)
            add(R.drawable.gradient_collage)
            add(R.drawable.ai_cloud_concept_with_robot_head)
            add(R.drawable.gradient_ai_cloud_with_broken_pieces)
            add(R.drawable.ai_cloud_concept_with_robot_face)
            add(R.drawable.ai_cloud_concept_with_robot_arm)
        }
        viewPager = binding.viewPager
        imageSliderAdapter = ImageSliderAdapter(viewPager, imageList)
        viewPager.apply {
            adapter = imageSliderAdapter
            offscreenPageLimit = imageList.size
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }

    private fun setupImageSliderTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.3f
        }
        viewPager.setPageTransformer(transformer)
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        cryptoViewModel.onClear()
//        geckoViewModel.onClear()
//        portfolioViewModel.onClear()
//    }

}