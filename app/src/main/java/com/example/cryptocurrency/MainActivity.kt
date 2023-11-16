package com.example.cryptocurrency

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.cryptocurrency.databinding.ActivityMainBinding
import com.example.cryptocurrency.databinding.FragmentSplashBinding
import com.example.cryptocurrency.view_model.CryptoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var splashBinding: FragmentSplashBinding
    private var backPressed: Long = 0
    private val viewModel: CryptoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        splashBinding = FragmentSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.bottomNav, navController)

        navController.addOnDestinationChangedListener { _, d, _ ->
            when (d.id) {
                R.id.homeFragment -> {
                    supportActionBar?.hide()
                    binding.bottomNav.isVisible = true
                }

                R.id.marketFragment -> {
                    supportActionBar?.hide()
                    binding.bottomNav.isVisible = true
                }

                R.id.portfolioFragment -> {
                    supportActionBar?.hide()
                    binding.bottomNav.isVisible = true
                }

                R.id.cryptoChartDetailFragment -> {
                    supportActionBar?.hide()
                    binding.bottomNav.isVisible = false
                }

                R.id.addTransactionFragment -> {
                    binding.bottomNav.isVisible = false
                }

                R.id.newsDetailsFragment -> {
                    binding.bottomNav.isVisible = false
                }

                R.id.nftsFragment -> {
                    binding.bottomNav.isVisible = false
                }

                R.id.geckoFragment -> {
                    binding.bottomNav.isVisible = true
                }

                R.id.blockSpanDetailFragment -> {
                    binding.bottomNav.isVisible = false
                }

                R.id.biometricFragment -> {
                    supportActionBar?.hide()
                    binding.bottomNav.isVisible = false
                }
            }
        }

        showSplash(navController = navController)

    }

    private fun showSplash(navController: NavController) {
        Handler(Looper.getMainLooper()).postDelayed({
            navController.popBackStack()
            navController.navigate(R.id.homeFragment)
        }, 5000)
        splashBinding.splashLottie.visibility = View.GONE
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val destination = findNavController(R.id.nav_host_fragment).currentDestination?.id
        val navController = findNavController(R.id.nav_host_fragment)
        if (destination == R.id.homeFragment) {
            if (backPressed + 2000 > System.currentTimeMillis()) {
                super.onBackPressed()
            } else {
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
                backPressed = System.currentTimeMillis()
            }
        } else {
            navController.popBackStack()
        }
        if (destination == R.id.biometricFragment) {
            super.onBackPressed()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

}