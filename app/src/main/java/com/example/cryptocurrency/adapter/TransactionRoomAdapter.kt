package com.example.cryptocurrency.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.HoldingItemBinding
import com.example.cryptocurrency.model.USD
import com.example.cryptocurrency.model.transaction.TransactionModel

class TransactionRoomAdapter :
    RecyclerView.Adapter<TransactionRoomAdapter.TransactionRoomViewHolder>() {

    private lateinit var binding: HoldingItemBinding

    var onItemLongClick: ((TransactionModel) -> Unit)? = null

    fun deleteItem(position: Int) {
        val transaction = differ.currentList.toMutableList()
        transaction.removeAt(position)
        differ.submitList(transaction)
    }

    class TransactionRoomViewHolder(val binding: HoldingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(trans: TransactionModel) {
            binding.trans = trans
            binding.executePendingBindings()
        }

        fun bindList(data: USD) {
            binding.crypto = data
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
        holder.bind(transactions)
        holder.itemView.setOnLongClickListener {
            onItemLongClick?.invoke(transactions)
            return@setOnLongClickListener true
        }
        Glide.with(holder.itemView.context)
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/${transactions.id}.png")
            .placeholder(R.drawable.loading3).into(binding.logo)

        if (transactions.percent_change > 0.000) {
            binding.percent.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.green
                )
            )
            binding.percent.text = String.format("+%.2f%%", transactions.percent_change)
        }
        if (transactions.percent_change < 0) {
            binding.percent.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.chili
                )
            )
            binding.percent.text = String.format("%.2f%%", transactions.percent_change)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffUtil = object : DiffUtil.ItemCallback<TransactionModel>() {

        override fun areItemsTheSame(
            oldItem: TransactionModel,
            newItem: TransactionModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TransactionModel,
            newItem: TransactionModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

}