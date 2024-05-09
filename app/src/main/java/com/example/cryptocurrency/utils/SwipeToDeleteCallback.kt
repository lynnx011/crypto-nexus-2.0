package com.example.cryptocurrency.utils
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrency.adapter.TransactionRoomAdapter
//import com.example.cryptocurrency.view_model.CryptoViewModel
//import com.example.cryptocurrency.view_model.PortfolioViewModel

class SwipeToDeleteCallback(private val adapter: TransactionRoomAdapter) : ItemTouchHelper.Callback() {
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
//        viewModel.totalTrans.value -= transaction.usd_amount
//        viewModel.totalHoldingValue.value = "$${context.cutOffPoint(viewModel.totalTrans.value?:0.0)}"
//        val alertDialog = AlertDialog.Builder(context)
//            .setTitle("Are you sure?")
//            .setMessage("Make sure to delete this currency!")
//        val clickListener = DialogInterface.OnClickListener { dialog, which ->
//            when(which){
//                DialogInterface.BUTTON_POSITIVE -> {
//                    viewModel.deleteTransaction(transaction)
//                    viewModel.totalTrans.value -= transaction.usd_amount
//                    viewModel.totalHoldingValue.value = "$${context.cutOffPoint(viewModel.totalTrans.value?:0.0)}"
//                }
//                DialogInterface.BUTTON_NEGATIVE -> {
//                    dialog.dismiss()
//                    viewModel.insertTransaction(transaction)
//                    viewModel.totalTrans.value += transaction.usd_amount
//                    viewModel.totalHoldingValue.value = "$${context.cutOffPoint(viewModel.totalTrans.value?:0.0)}"
//                }
//            }
//        }
//        alertDialog.setPositiveButton("Delete",clickListener)
//        alertDialog.setNegativeButton("Cancel",clickListener)
//        alertDialog.show()
//        viewModel.deleteTransaction(transaction)
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }
}