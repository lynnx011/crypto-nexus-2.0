package com.example.cryptocurrency.presentation.screens
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.cryptocurrency.R
import com.example.cryptocurrency.adapter.GeckoPagerAdapter
import com.example.cryptocurrency.databinding.FragmentGeckoBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GeckoFragment : Fragment() {
    private lateinit var binding: FragmentGeckoBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var geckoPagerAdapter: GeckoPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_gecko,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout = binding.tabLayout
        viewPager2 = binding.viewPager2

        geckoPagerAdapter = GeckoPagerAdapter(childFragmentManager,lifecycle)

        tabLayout.addTab(tabLayout.newTab().setText("Trending News"))
        tabLayout.addTab(tabLayout.newTab().setText("Listing NFTs"))

        viewPager2.adapter = geckoPagerAdapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null)
                    viewPager2.currentItem = tab.position
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
    }
}