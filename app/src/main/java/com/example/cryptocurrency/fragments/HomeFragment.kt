package com.example.cryptocurrency.fragments
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
import com.example.cryptocurrency.utils.ImageSliderAdapter
import com.example.cryptocurrency.view_model.CryptoViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var cryptoAdapter: TopCryptoAdapter
    private lateinit var viewPager2: ViewPager2
    private lateinit var viewPager: ViewPager2
    private lateinit var handler: Handler
    private lateinit var tabLayout: TabLayout
    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private lateinit var imageList: ArrayList<Int>
    private lateinit var pagerAdapter: GainLossPagerAdapter
    private val networkDetector  by lazy { context?.let { NetworkDetector(it) } }

    val cryptoViewModel : CryptoViewModel by viewModels()

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable,4000)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handler = Handler(Looper.getMainLooper())
        binding.topCircular.visibility = View.VISIBLE

        topCryptoRecyclerView()

        setupImageSlider()

        setupImageSliderTransformer()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable,4000)
            }
        })

        networkDetector?.observe(viewLifecycleOwner){ isConnected ->
            if (isConnected){
                lifecycleScope.launch {
                    cryptoViewModel.topCryptosLiveData.observe(viewLifecycleOwner){ data ->
                        cryptoAdapter.differ.submitList(data)
                        binding.topCircular.isVisible = false
                        Log.d("data",data.toString())
                    }
                }

                cryptoViewModel.getTopCryptos()
            }else{
                Toast.makeText(requireContext(),"Disconnected",Toast.LENGTH_SHORT).show()
            }
        }

        tabLayout = binding.tabLayout
        viewPager2 = binding.viewPager2
        pagerAdapter = GainLossPagerAdapter(childFragmentManager,lifecycle)

        tabLayout.addTab(tabLayout.newTab().setText("Top Gainers"))
        tabLayout.addTab(tabLayout.newTab().setText("Top Losers"))

        viewPager2.adapter = pagerAdapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{

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

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        cryptoAdapter.onItemClick = { crypto ->
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

    private val runnable = Runnable {
        viewPager.currentItem = viewPager.currentItem + 1
    }

    private fun topCryptoRecyclerView(){
        binding.recView.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                cryptoAdapter = TopCryptoAdapter()
                adapter = cryptoAdapter
        }
    }

    private fun setupImageSlider(){
        imageList = ArrayList()
        imageList.add(R.drawable.ai_cloud_with_robot_face)
        imageList.add(R.drawable.cryptocurrency_bitcoin)
        imageList.add(R.drawable.gradient_collage)
        imageList.add(R.drawable.ai_cloud_concept_with_robot_head)
        imageList.add(R.drawable.gradient_ai_cloud_with_broken_pieces)
        imageList.add(R.drawable.ai_cloud_concept_with_robot_face)
        imageList.add(R.drawable.ai_cloud_concept_with_robot_arm)
        viewPager = binding.viewPager
        imageSliderAdapter = ImageSliderAdapter(viewPager,imageList)
        viewPager.apply {
            adapter = imageSliderAdapter
            offscreenPageLimit = imageList.size
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }

    private fun setupImageSliderTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer{page,position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.3f
        }
        viewPager.setPageTransformer(transformer)
    }


}