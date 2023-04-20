package com.example.cryptocurrency.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.NewsItemBinding
import com.example.cryptocurrency.model.model5.Article

class CryptoNewsAdapter : RecyclerView.Adapter<CryptoNewsAdapter.CryptoNewsViewHolder>() {

    private lateinit var binding: NewsItemBinding
    var onItemClick: ((Article) -> Unit)? = null

    class CryptoNewsViewHolder(val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(news: Article) {
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
        holder.bindData(news)
        Glide.with(holder.itemView.context)
            .load(news.urlToImage)
            .into(holder.binding.thumbnail)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(news)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.author == newItem.author
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)
}