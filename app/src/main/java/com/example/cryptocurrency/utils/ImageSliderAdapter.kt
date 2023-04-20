package com.example.cryptocurrency.utils
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.ImageSliderItemBinding

class ImageSliderAdapter(
    private val viewPager2: ViewPager2,
    private val imageList: ArrayList<Int>
) : RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>() {

    private lateinit var binding: ImageSliderItemBinding

    @SuppressLint("NotifyDataSetChanged")
    private val runnable = Runnable {
        imageList.addAll(imageList)
        notifyDataSetChanged()
    }

    class ImageSliderViewHolder(val binding: ImageSliderItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.image_slider_item, parent, false)
        return ImageSliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(imageList[position]).into(binding.imgView)
        if (position == imageList.size - 1) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

}