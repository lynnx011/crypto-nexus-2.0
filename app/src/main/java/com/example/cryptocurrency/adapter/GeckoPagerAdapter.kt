package com.example.cryptocurrency.adapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cryptocurrency.fragments.NewsFragment
import com.example.cryptocurrency.fragments.NftsFragment

class GeckoPagerAdapter(val fragmentManager: FragmentManager,val lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            NewsFragment()
        else
            NftsFragment()
    }
}