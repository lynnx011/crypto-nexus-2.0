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
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.TransactionItemBinding
import com.example.cryptocurrency.model.CryptoDetails
import com.example.cryptocurrency.model.USD
import java.util.*

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>(),Filterable {

    private lateinit var binding: TransactionItemBinding

    var cryptoList = emptyList<CryptoDetails>()
    var filteredList = emptyList<CryptoDetails>()

    var onItemClick : ((CryptoDetails) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun updateFilteredList(items: List<CryptoDetails>){
        cryptoList = items
        filteredList = items
        differ.submitList(items)
        notifyDataSetChanged()
    }

    class TransactionViewHolder(val binding: TransactionItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindName(crypto: CryptoDetails){
            binding.crypto = crypto
            binding.executePendingBindings()
        }

        fun bindSymbol(crypto: USD){
            binding.crypto1 = crypto
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.transaction_item,parent,false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val cryptos = differ.currentList[position]
        holder.apply {
            bindName(cryptos)
            bindSymbol(cryptos.quote.USD)
            itemView.setOnClickListener {
                onItemClick?.invoke(cryptos)
            }
        }

    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    private val diffUtil = object : DiffUtil.ItemCallback<CryptoDetails>(){

        override fun areItemsTheSame(oldItem: CryptoDetails, newItem: CryptoDetails): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CryptoDetails, newItem: CryptoDetails): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)
    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(text: CharSequence?): FilterResults {
                val query = text.toString().trim().lowercase(Locale.getDefault())
                filteredList = if (query.isNotEmpty()){
                    cryptoList.filter { crypto ->
                        crypto.symbol.lowercase(Locale.getDefault()).contains(query) ||
                                crypto.name.lowercase(Locale.getDefault()).contains(query)
                    }
                }
                else{
                    cryptoList
                }
                return FilterResults().apply {
                    values = filteredList
                }
            }

            override fun publishResults(text: CharSequence?, result: FilterResults?) {
                cryptoList = result?.values as List<CryptoDetails>
                differ.submitList(cryptoList)
            }

        }
    }


}