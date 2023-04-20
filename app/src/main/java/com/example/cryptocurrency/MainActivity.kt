package com.example.cryptocurrency
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.cryptocurrency.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var backPressed: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.bottomNav,navController)

        navController.addOnDestinationChangedListener{_,d,_ ->
            when(d.id){
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
            }
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val destination = findNavController(R.id.nav_host_fragment).currentDestination?.id
        val navController = findNavController(R.id.nav_host_fragment)
        if (destination == R.id.homeFragment){
            if (backPressed + 2000 > System.currentTimeMillis()){
                super.onBackPressed()
            }else{
                Toast.makeText(this,"Press back again to exit",Toast.LENGTH_SHORT).show()
                backPressed = System.currentTimeMillis()
            }
        }else{
            navController.popBackStack()
        }
    }
}