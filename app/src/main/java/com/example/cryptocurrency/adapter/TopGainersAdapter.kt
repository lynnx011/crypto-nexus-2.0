package com.example.cryptocurrency.adapter
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.TopGainersItemBinding
import com.example.cryptocurrency.model.CryptoDetails
import com.example.cryptocurrency.model.USD
import java.util.*

class TopGainersAdapter(private val onItemClick: ((CryptoDetails) -> Unit)?): RecyclerView.Adapter<TopGainersAdapter.TopGainersViewHolder>(),Filterable {
    private lateinit var binding: TopGainersItemBinding

   var cryptoList = emptyList<CryptoDetails>()
    var filteredItems = emptyList<CryptoDetails>()
    @SuppressLint("NotifyDataSetChanged")
    fun updateFilteredItem(items: List<CryptoDetails>){
        cryptoList = items
        filteredItems = items
        differ.submitList(items)
        notifyDataSetChanged()
    }

    class TopGainersViewHolder(val binding: TopGainersItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(crypto: CryptoDetails){
            binding.crypto = crypto
            binding.executePendingBindings()
        }

        fun bindData(data: USD){
            binding.crypto1 = data
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopGainersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.top_gainers_item,parent,false)
        return TopGainersViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TopGainersViewHolder, position: Int) {
        val cryptos = differ.currentList[position]
        holder.bind(cryptos)
        holder.bindData(cryptos.quote.USD)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(cryptos)
        }
        Glide.with(holder.itemView)
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/${cryptos.id}.png")
            .thumbnail(Glide.with(holder.itemView.context).load(R.drawable.loading3))
            .into(holder.binding.currencyLogo)

        Glide.with(holder.itemView)
            .load("https://s3.coinmarketcap.com/generated/sparklines/web/1d/usd/${cryptos.id}.png")
            .thumbnail(Glide.with(holder.itemView.context).load(R.drawable.more))
            .into(holder.binding.currencyChart)

        if (cryptos.quote.USD.percent_change_24h > 0.000){
            binding.percent.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.green))
            binding.percent.text = String.format("+%.2f%%",cryptos.quote.USD.percent_change_24h)
        }
        if (cryptos.quote.USD.percent_change_24h < 0){
            binding.percent.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.chili))
            binding.percent.text = String.format("%.2f%%",cryptos.quote.USD.percent_change_24h)
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
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(query: CharSequence?): FilterResults {
                val queryText = query.toString().trim().lowercase(Locale.getDefault())
                    filteredItems = if (query!!.isNotEmpty()) {
                        cryptoList.filter { crypto ->
                            crypto.symbol.lowercase(Locale.getDefault()).contains(queryText) || crypto.name.lowercase(
                                Locale.getDefault()).contains(queryText)
                        }
                    } else {
                        cryptoList
                    }
                return FilterResults().apply {
                    values = filteredItems
                }
            }

            override fun publishResults(query: CharSequence?, result: FilterResults?) {
                    cryptoList = result?.values as List<CryptoDetails>
                    differ.submitList(cryptoList)
            }
        }

    }

}