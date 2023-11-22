package com.example.cryptocurrency.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.TopCryptoItemsBinding
import com.example.cryptocurrency.model.CryptoDetails
import com.example.cryptocurrency.model.USD
import com.example.cryptocurrency.utils.cryptoLogoUrl
import com.example.cryptocurrency.utils.cryptoSparklineUrl
import com.example.cryptocurrency.utils.setTextColor

class TopCryptoAdapter(private val onItemClick: ((CryptoDetails) -> Unit)? = null) :
    RecyclerView.Adapter<TopCryptoAdapter.TopCryptoViewHolder>() {
    private lateinit var binding: TopCryptoItemsBinding

    class TopCryptoViewHolder(val binding: TopCryptoItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(crypto: CryptoDetails) {
            binding.crypto = crypto
            binding.executePendingBindings()
        }

        fun bindData(data: USD) {
            binding.crypto1 = data
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopCryptoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.top_crypto_items, parent, false)
        return TopCryptoViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TopCryptoViewHolder, position: Int) {
        val cryptos = differ.currentList[position]
        val context = holder.itemView.context
        val change24h = cryptos.quote.USD.percent_change_24h
        holder.bind(cryptos)
        holder.bindData(cryptos.quote.USD)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(cryptos)
        }
        Glide.with(context)
            .load(cryptoLogoUrl(cryptos.id))
            .thumbnail(Glide.with(context).load(R.drawable.loading3))
            .into(holder.binding.cryptoLogo)

        Glide.with(context)
            .load(cryptoSparklineUrl(cryptos.id))
            .thumbnail(Glide.with(context).load(R.drawable.more))
            .into(holder.binding.cryptoChart)

        if ( change24h > 0.000) {
            holder.binding.change24h.setTextColor(
                context,
                R.color.green
            )
            holder.binding.change24h.text = String.format("+%.2f%%", change24h)
        }
        if (change24h < 0) {
            holder.binding.change24h.setTextColor(
                context,
                R.color.chili
            )
            holder.binding.change24h.text = String.format("%.2f%%", change24h)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffCallback = object : DiffUtil.ItemCallback<CryptoDetails>() {

        override fun areItemsTheSame(oldItem: CryptoDetails, newItem: CryptoDetails): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CryptoDetails, newItem: CryptoDetails): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffCallback)
}