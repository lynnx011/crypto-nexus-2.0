package com.example.cryptocurrency.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.BlockNftItemBinding
import com.example.cryptocurrency.model.model4.Result
import com.example.cryptocurrency.model.model4.Stats
import com.example.cryptocurrency.utils.loadImage

class BlockSpanAdapter(private val onItemClick: ((Result, Stats) -> Unit)?) :
    RecyclerView.Adapter<BlockSpanAdapter.BlockSpanViewHolder>() {

    lateinit var binding: BlockNftItemBinding

    class BlockSpanViewHolder(val binding: BlockNftItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.nft = result
            binding.executePendingBindings()
        }

        fun bindData(stat: Stats) {
            binding.stat = stat
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockSpanViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.block_nft_item, parent, false)
        return BlockSpanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BlockSpanViewHolder, position: Int) {
        val nft = differ.currentList[position]
        val context = holder.itemView.context
        holder.bind(nft)
        holder.bindData(nft.stats)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(nft, nft.stats)
        }

        Glide.with(context).load(nft.image_url).placeholder(R.drawable.nft1)
            .into(holder.binding.profile)
        Glide.with(context).load(nft.featured_image_url)
            .placeholder(R.drawable.img_1).into(holder.binding.featurePhoto)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Result>() {

        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)
}