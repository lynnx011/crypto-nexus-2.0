package com.example.cryptocurrency.adapter
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.TopGainersItemBinding
import com.example.cryptocurrency.domain.model.CryptoDetails
import com.example.cryptocurrency.domain.model.CryptoUsd
import com.example.cryptocurrency.utils.cryptoLogoUrl
import com.example.cryptocurrency.utils.cryptoSparklineUrl
import com.example.cryptocurrency.utils.setTextColor
import java.util.*

class TopGainersAdapter(private val onItemClick: ((CryptoDetails) -> Unit)?): RecyclerView.Adapter<TopGainersAdapter.TopGainersViewHolder>(),Filterable {
    private lateinit var binding: TopGainersItemBinding

    var cryptoList = emptyList<CryptoDetails?>()
    var filteredItems = emptyList<CryptoDetails?>()
    @SuppressLint("NotifyDataSetChanged")
    fun updateFilteredItem(items: List<CryptoDetails?>?){
        cryptoList = items ?: emptyList()
        filteredItems = items ?: emptyList()
        differ.submitList(items)
        notifyDataSetChanged()
    }

    class TopGainersViewHolder(val binding: TopGainersItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(crypto: CryptoDetails?){
            binding.crypto = crypto
            binding.executePendingBindings()
        }

        fun bindData(data: CryptoUsd?){
            binding.usd = data
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
        val context = holder.itemView.context
        val change24h = cryptos.quote?.usd?.percentChange24h
        holder.bind(cryptos)
        holder.bindData(cryptos.quote?.usd)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(cryptos)
        }
        holder.binding.percent.text = String.format("+%.2f%%",change24h)
        Glide.with(context)
            .load(cryptoLogoUrl(cryptos.id))
            .thumbnail(Glide.with(context).load(R.drawable.loading3))
            .into(holder.binding.currencyLogo)

        Glide.with(context)
            .load(cryptoSparklineUrl(cryptos.id))
            .thumbnail(Glide.with(context).load(R.drawable.more))
            .into(holder.binding.currencyChart)

        if ( (change24h?:0.0) > 0.000) {
            holder.binding.percent.setTextColor(
                context,
                R.color.green
            )
            holder.binding.percent.text = String.format("+%.2f%%", change24h)
        }
        if ((change24h?:0.0) < 0) {
            holder.binding.percent.setTextColor(
                context,
                R.color.chili
            )
            holder.binding.percent.text = String.format("%.2f%%", change24h)
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
                        crypto?.symbol?.lowercase(Locale.getDefault())?.contains(queryText) == true || crypto?.name?.lowercase(
                            Locale.getDefault())?.contains(queryText) == true
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