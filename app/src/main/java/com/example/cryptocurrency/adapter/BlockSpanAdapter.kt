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

class BlockSpanAdapter : RecyclerView.Adapter<BlockSpanAdapter.BlockSpanViewHolder>() {

    lateinit var binding: BlockNftItemBinding
    var onItemClick : ((Result,Stats) -> Unit)? = null

    class BlockSpanViewHolder(val binding: BlockNftItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(result: Result){
            binding.nft = result
            binding.executePendingBindings()
        }

        fun bindData(stat: Stats){
            binding.stat = stat
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockSpanViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.block_nft_item,parent,false)
        return BlockSpanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BlockSpanViewHolder, position: Int) {
        val nfts = differ.currentList[position]
        holder.bind(nfts)
        holder.bindData(nfts.stats)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(nfts,nfts.stats)
        }
        Glide.with(holder.itemView.context).load(nfts.image_url).placeholder(R.drawable.nft1).into(holder.binding.profile)
        Glide.with(holder.itemView.context).load(nfts.featured_image_url).placeholder(R.drawable.img_1).into(holder.binding.featurePhoto)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Result>(){

        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
           return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)
}