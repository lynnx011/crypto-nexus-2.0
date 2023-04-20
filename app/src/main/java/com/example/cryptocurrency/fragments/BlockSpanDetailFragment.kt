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
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.FragmentBlockSpanDetailBinding

class BlockSpanDetailFragment : Fragment() {
    private lateinit var binding: FragmentBlockSpanDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_block_span_detail,container,false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        val studioName = args?.getString("studio")
        val project = args?.getString("project")
        val desc = args?.getString("description")
        val imageUrl = args?.getString("profileImage")
        val featuredUrl = args?.getString("featuredImage")
        val supply = args?.getString("totalSupply")
        val owners = args?.getString("owners")
        val volume = args?.getString("totalVolume")
        val exchangeUrl = args?.getString("exchangeUrl")

        binding.apply {
            if (studioName != null){
                studio.text = studioName
                creator.text = studioName
            }
            if (project != null){
                projectName.text = project
                toolbarTitle.text = "Details of $project"
            }
            if (desc != null){
                description.text = desc
            }
            if (imageUrl != null){
                loadImage(profile,imageUrl)
            }
            if (featuredUrl != null){
                loadImage(featured,featuredUrl)
            }
            if (supply != null){
                totalSupply.text = supply
            }
            if (owners != null){
                totalOwner.text = owners
            }
            if (volume != null){
                val formatVolume = String.format("%.2f",volume.toDouble())
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

    private fun loadImage(view: ImageView, url: String){
        Glide.with(requireContext())
            .load(url)
            .placeholder(R.drawable.img_1)
            .into(view)
    }


}