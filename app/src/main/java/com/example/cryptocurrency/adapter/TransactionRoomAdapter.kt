package com.example.cryptocurrency.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.HoldingItemBinding
import com.example.cryptocurrency.domain.model.Transaction
import com.example.cryptocurrency.utils.loadImage
import com.example.cryptocurrency.utils.setTextColor

class TransactionRoomAdapter(private val onItemClick: ((Transaction) -> Unit)?) :
    RecyclerView.Adapter<TransactionRoomAdapter.TransactionRoomViewHolder>() {

    private lateinit var binding: HoldingItemBinding


    fun deleteItem(position: Int) {
        val transaction = differ.currentList.toMutableList()
        transaction.removeAt(position)
        differ.submitList(transaction)
    }

    class TransactionRoomViewHolder(val binding: HoldingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(trans: Transaction) {
            binding.trans = trans
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionRoomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.holding_item, parent, false)
        return TransactionRoomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionRoomViewHolder, position: Int) {
        val transactions = differ.currentList[position]
        val context = holder.itemView.context
        holder.bind(transactions)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(transactions)
        }

        binding.logo.loadImage(context, "https://s2.coinmarketcap.com/static/img/coins/64x64/${transactions.id}.png", R.drawable.loading3)

        if (transactions.percentChange > 0.000) {

            binding.percent.apply {
                this.setTextColor(context, R.color.green)
                this.text = String.format("+%.2f%%", transactions.percentChange)
            }

        }
        if (transactions.percentChange < 0) {
            binding.percent.apply {
                this.setTextColor(context, R.color.chili)
                this.text = String.format("%.2f%%", transactions.percentChange)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Transaction>() {

        override fun areItemsTheSame(
            oldItem: Transaction,
            newItem: Transaction
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Transaction,
            newItem: Transaction
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

}