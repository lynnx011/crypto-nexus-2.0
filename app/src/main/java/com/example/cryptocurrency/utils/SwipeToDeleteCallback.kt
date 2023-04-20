package com.example.cryptocurrency.utils
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrency.adapter.TransactionRoomAdapter
import com.example.cryptocurrency.view_model.CryptoViewModel

class SwipeToDeleteCallback(private val viewModel: CryptoViewModel,private val adapter: TransactionRoomAdapter) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val swipeFlags = ItemTouchHelper.LEFT
        return makeMovementFlags(0, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.absoluteAdapterPosition
        val transaction = adapter.differ.currentList[position]
        viewModel.deleteTransaction(transaction)
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }
}