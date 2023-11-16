package com.example.cryptocurrency.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrency.R
import com.example.cryptocurrency.adapter.TransactionRoomAdapter
import com.example.cryptocurrency.databinding.FragmentPortfolioBinding
import com.example.cryptocurrency.utils.SwipeToDeleteCallback
import com.example.cryptocurrency.utils.cutOffPoint
import com.example.cryptocurrency.view_model.CryptoViewModel
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PortfolioFragment : Fragment() {
    private lateinit var binding: FragmentPortfolioBinding
    private lateinit var transactionRoomAdapter: TransactionRoomAdapter
    private val transactionRoomViewModel: CryptoViewModel by activityViewModels()
    private lateinit var swipeToDeleteCallback: SwipeToDeleteCallback
    private lateinit var remoteConfig: FirebaseRemoteConfig
    private lateinit var remoteConfigSettings: FirebaseRemoteConfigSettings
    var isHide = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_portfolio, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
            transactionRoomAdapter = TransactionRoomAdapter {
                val bundle = bundleOf("portfolio_name" to it.name, "portfolio_symbol" to it.symbol)
                findNavController().navigate(R.id.cryptoChartDetailFragment, bundle)
            }
            adapter = transactionRoomAdapter
        }
        swipeToDeleteCallback =
            SwipeToDeleteCallback(transactionRoomViewModel, transactionRoomAdapter)
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.recView)

        transactionRoomViewModel.roomTransaction()
            .observe(viewLifecycleOwner) { transaction ->
                transactionRoomAdapter.differ.submitList(transaction)
                var totalTrans = 0.0
                for (i in transaction) {
                    totalTrans += i.usd_amount
                }
                transactionRoomViewModel.totalHoldingValue.value = "$${cutOffPoint(totalTrans)}"

            }

        transactionRoomViewModel.totalHoldingValue.observe(viewLifecycleOwner) { value ->
            binding.totalValue.text = value
        }

        binding.addAsset.setOnClickListener {
            findNavController().navigate(R.id.action_portfolioFragment_to_addTransactionFragment)
        }

        remoteConfig = FirebaseRemoteConfig.getInstance()
        remoteConfigSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(0)
            .build()

        remoteConfig.setConfigSettingsAsync(remoteConfigSettings)
        remoteConfig.setDefaultsAsync(R.xml.firebase_remote_config_defaults)
        fetchRemoteParams()

    }

    @SuppressLint("SetTextI18n")
    private fun fetchRemoteParams() {
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
//                binding.balance.text = "$" + remoteConfig.getString("current_balance")
//                binding.lottie.setAnimationFromJson(remoteConfig.getString("lottie_anim"), "lottie")
                binding.lottie.setFailureListener { throwable ->
                    Log.d("config", throwable.toString())
                }
                val updated = task.result
                Log.d("config", "updated $updated")
            } else {
                Log.d("config", "failed")
            }
        }
    }


}