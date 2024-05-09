package com.example.cryptocurrency.adapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cryptocurrency.presentation.screens.TopGainersFragment
import com.example.cryptocurrency.presentation.screens.TopLosersFragment

class GainLossPagerAdapter(manager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(manager,lifecycle){

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            TopGainersFragment()
        }
        else {
            TopLosersFragment()
        }
    }
}