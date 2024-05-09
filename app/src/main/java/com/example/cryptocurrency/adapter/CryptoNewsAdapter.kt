package com.example.cryptocurrency.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.NewsItemBinding
import com.example.cryptocurrency.domain.model.NftArticle
import com.example.cryptocurrency.utils.loadImg

class CryptoNewsAdapter(private val onItemClick: ((NftArticle) -> Unit)?) :
    RecyclerView.Adapter<CryptoNewsAdapter.CryptoNewsViewHolder>() {

    private lateinit var binding: NewsItemBinding

    class CryptoNewsViewHolder(val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(news: NftArticle) {
            binding.news = news
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoNewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.news_item, parent, false)
        return CryptoNewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoNewsViewHolder, position: Int) {
        val news = differ.currentList[position]
        val context = holder.itemView.context
        holder.bindData(news)
        binding.thumbnail.loadImg(context, news.urlToImage.orEmpty())
        holder.itemView.setOnClickListener {
            news?.let(holder::bindData)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffUtil = object : DiffUtil.ItemCallback<NftArticle>() {

        override fun areItemsTheSame(oldItem: NftArticle, newItem: NftArticle): Boolean {
            return oldItem.author == newItem.author
        }

        override fun areContentsTheSame(oldItem: NftArticle, newItem: NftArticle): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)
}