package com.example.cryptocurrency.presentation.screens
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
import com.example.cryptocurrency.presentation.view_models.BlockSpanViewModel
import com.example.cryptocurrency.utils.collectLatest
import com.example.cryptocurrency.utils.loadImage
import com.example.cryptocurrency.utils.popBack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlockSpanDetailFragment : Fragment() {
    private lateinit var binding: FragmentBlockSpanDetailBinding
    private val viewModel: BlockSpanViewModel by activityViewModels()

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

        setupBlockSpanDetails()

        binding.backKey.setOnClickListener {
            popBack()
        }
    }

    private fun setupBlockSpanDetails(){
        viewModel.blockSpanUiState.collectLatest(viewLifecycleOwner){ details ->
            binding.apply {
                studio.text = details.studioName
                creator.text = details.studioName
                projectName.text = details.project
                toolbarTitle.text = if (details.project.isNotEmpty()) "Details of ${details.project}" else "Details"
                description.text = details.desc
                totalSupply.text = details.supply.toString()
                totalOwner.text = details.owners.toString()
                val volume = String.format("%.2f", details.volume)
                totalVolume.text = volume

                if (details.imageUrl.isNotEmpty()) {
                    profile.loadImage(requireContext(), details.imageUrl, R.drawable.img_1)
                }
                if (details.featuredUrl.isNotEmpty()) {
                    featured.loadImage(requireContext(), details.featuredUrl, R.drawable.img_1)
                }

                opensea.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(details.exchangeUrl))
                    startActivity(intent)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.blockSpanResult.value = null
        viewModel.blockSpanStats.value = null
    }


}