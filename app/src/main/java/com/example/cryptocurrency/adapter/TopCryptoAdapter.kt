package com.example.cryptocurrency.adapter
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.TopCryptoItemsBinding
import com.example.cryptocurrency.model.CryptoDetails
import com.example.cryptocurrency.model.USD

class TopCryptoAdapter : RecyclerView.Adapter<TopCryptoAdapter.TopCryptoViewHolder>() {
    private lateinit var binding: TopCryptoItemsBinding

    var onItemClick : ((CryptoDetails) -> Unit)? = null

    class TopCryptoViewHolder(val binding: TopCryptoItemsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(crypto: CryptoDetails){
            binding.crypto = crypto
            binding.executePendingBindings()
        }

        fun bindData(data: USD){
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
        holder.bind(cryptos)
        holder.bindData(cryptos.quote.USD)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(cryptos)
        }
        Glide.with(holder.itemView.context)
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/" + cryptos.id + ".png")
            .thumbnail(Glide.with(holder.itemView.context).load(R.drawable.loading3))
            .into(holder.binding.cryptoLogo)

        Glide.with(holder.itemView.context)
            .load("https://s3.coinmarketcap.com/generated/sparklines/web/1d/usd/${cryptos.id}.png")
            .thumbnail(Glide.with(holder.itemView.context).load(R.drawable.more))
            .into(holder.binding.cryptoChart)

        if (cryptos.quote.USD.percent_change_24h > 0.000){
            binding.change24h.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.green))
            binding.change24h.text = "+${cryptos.quote.USD.percent_change_24h}%"
        }
        if (cryptos.quote.USD.percent_change_24h < 0){
            binding.change24h.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.chili))
            binding.change24h.text = "${cryptos.quote.USD.percent_change_24h}%"
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffCallback = object : DiffUtil.ItemCallback<CryptoDetails>(){

        override fun areItemsTheSame(oldItem: CryptoDetails, newItem: CryptoDetails): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CryptoDetails, newItem: CryptoDetails): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffCallback)
}