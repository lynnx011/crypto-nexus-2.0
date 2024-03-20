package com.example.cryptocurrency.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.FragmentBlockSpanDetailBinding
import com.example.cryptocurrency.utils.loadImage
import com.example.cryptocurrency.utils.popBack
import com.example.cryptocurrency.view_model.GeckoViewModel

class BlockSpanDetailFragment : Fragment() {
    private lateinit var binding: FragmentBlockSpanDetailBinding
    private val nftViewModel: GeckoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_block_span_detail, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val studioName = nftViewModel.nftResult.value?.key
        val project = nftViewModel.nftResult.value?.name
        val desc = nftViewModel.nftResult.value?.description
        val imageUrl = nftViewModel.nftResult.value?.image_url
        val featuredUrl = nftViewModel.nftResult.value?.featured_image_url
        val supply = nftViewModel.nftStats.value?.total_supply
        val owners = nftViewModel.nftStats.value?.num_owners
        val volume = nftViewModel.nftStats.value?.total_volume
        val exchangeUrl = nftViewModel.nftResult.value?.exchange_url

        binding.apply {
                studio.text = studioName.orEmpty()
                creator.text = studioName.orEmpty()
            projectName.text = project.orEmpty()
            toolbarTitle.text = if (project != null) "Details of $project" else "Details"
            description.text = desc.orEmpty()
            totalSupply.text = (supply ?: 0).toString()
            totalOwner.text = (owners ?: 0).toString()
            val formatVolume = String.format("%.2f", volume ?: 0.0)
            totalVolume.text = formatVolume

            if (imageUrl != null) {
                profile.loadImage(requireContext(), imageUrl, R.drawable.img_1)
            }
            if (featuredUrl != null) {
                featured.loadImage(requireContext(), featuredUrl, R.drawable.img_1)
            }

            opensea.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(exchangeUrl))
                startActivity(intent)
            }
        }

        binding.backKey.setOnClickListener {
            popBack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        nftViewModel.nftResult.value = null
        nftViewModel.nftStats.value = null
    }


}