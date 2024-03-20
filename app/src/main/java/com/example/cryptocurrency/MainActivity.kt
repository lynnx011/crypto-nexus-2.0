package com.example.cryptocurrency

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.cryptocurrency.databinding.ActivityMainBinding
import com.example.cryptocurrency.databinding.FragmentSplashBinding
import com.example.cryptocurrency.utils.showToast
import com.example.cryptocurrency.view_model.CryptoViewModel
import com.example.cryptocurrency.view_model.PortfolioViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var splashBinding: FragmentSplashBinding
    private var backPressed: Long = 0
    private val viewModel: CryptoViewModel by viewModels()
    private val portfolioViewModel: PortfolioViewModel by viewModels()
    private val geckoViewModel: PortfolioViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        splashBinding = FragmentSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.bottomNav, navController)

        navController.addOnDestinationChangedListener { _, d, _ ->
            when (d.id) {
                R.id.nav_home,R.id.nav_market,R.id.nav_portfolio -> {
                    supportActionBar?.hide()
                    binding.bottomNav.isVisible = true
                }

                R.id.nav_chart_details,R.id.nav_biometric -> {
                    supportActionBar?.hide()
                    binding.bottomNav.isVisible = false
                }

                R.id.nav_add_trans,R.id.nav_news_details,R.id.nav_nft,R.id.nav_block_span_details -> {
                    binding.bottomNav.isVisible = false
                }

                R.id.nav_gecko -> {
                    binding.bottomNav.isVisible = true
                }

            }
        }

        showSplash(navController = navController)

    }

    private fun showSplash(navController: NavController) {
        Handler(Looper.getMainLooper()).postDelayed({
            navController.popBackStack()
            navController.navigate(R.id.nav_home)
        }, 5000)
        splashBinding.splashLottie.visibility = View.GONE
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val destination = findNavController(R.id.nav_host_fragment).currentDestination?.id
        val navController = findNavController(R.id.nav_host_fragment)
        if (destination == R.id.nav_home || destination == R.id.nav_market || destination == R.id.nav_gecko || destination == R.id.nav_portfolio) {
            if (backPressed + 2000 > System.currentTimeMillis()) {
                super.onBackPressed()
            } else {
                showToast("Press back again to exit")
                backPressed = System.currentTimeMillis()
            }
        } else {
            navController.popBackStack()
        }
        if (destination == R.id.nav_biometric) {
            super.onBackPressed()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

}