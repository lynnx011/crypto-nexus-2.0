package com.example.cryptocurrency.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.BlockNftItemBinding
import com.example.cryptocurrency.domain.model.BlockSpanResult
import com.example.cryptocurrency.domain.model.BlockSpanStats

class BlockSpanAdapter(private val onItemClick: ((BlockSpanResult?, BlockSpanStats?) -> Unit)?) :
    RecyclerView.Adapter<BlockSpanAdapter.BlockSpanViewHolder>() {

    lateinit var binding: BlockNftItemBinding

    class BlockSpanViewHolder(val binding: BlockNftItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: BlockSpanResult?) {
            binding.nft = result
            binding.executePendingBindings()
        }

        fun bindData(stat: BlockSpanStats?) {
            binding.stat = stat
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockSpanViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BlockNftItemBinding.inflate(inflater,parent,false)
        return BlockSpanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BlockSpanViewHolder, position: Int) {
        val nft = differ.currentList[position]
        val context = holder.itemView.context
        nft?.let(holder::bind)
        nft?.stats?.let(holder::bindData)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(nft, nft.stats)
        }

        Glide.with(context).load(nft.imageUrl).placeholder(R.drawable.nft1)
            .into(holder.binding.profile)
        Glide.with(context).load(nft.featuredImageUrl)
            .placeholder(R.drawable.img_1).into(holder.binding.featurePhoto)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffUtil = object : DiffUtil.ItemCallback<BlockSpanResult>() {

        override fun areItemsTheSame(oldItem: BlockSpanResult, newItem: BlockSpanResult): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(oldItem: BlockSpanResult, newItem: BlockSpanResult): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)
}