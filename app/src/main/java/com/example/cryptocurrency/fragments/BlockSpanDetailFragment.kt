package com.example.cryptocurrency.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.FragmentBlockSpanDetailBinding
import com.example.cryptocurrency.view_model.CryptoViewModel

class BlockSpanDetailFragment : Fragment() {
    private lateinit var binding: FragmentBlockSpanDetailBinding
    private val nftViewModel: CryptoViewModel by activityViewModels()

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
            if (studioName != null) {
                studio.text = studioName
                creator.text = studioName
            }
            if (project != null) {
                projectName.text = project
                toolbarTitle.text = "Details of $project"
            }
            if (desc != null) {
                description.text = desc
            }
            if (imageUrl != null) {
                loadImage(profile, imageUrl)
            }
            if (featuredUrl != null) {
                loadImage(featured, featuredUrl)
            }
            if (supply != null) {
                totalSupply.text = supply.toString()
            }
            if (owners != null) {
                totalOwner.text = owners.toString()
            }
            if (volume != null) {
                val formatVolume = String.format("%.2f", volume.toDouble())
                totalVolume.text = formatVolume
            }

            opensea.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(exchangeUrl))
                startActivity(intent)
            }
        }

        binding.backKey.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun loadImage(view: ImageView, url: String) {
        Glide.with(requireContext())
            .load(url)
            .placeholder(R.drawable.img_1)
            .into(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        nftViewModel.nftResult.value = null
        nftViewModel.nftStats.value = null
    }


}