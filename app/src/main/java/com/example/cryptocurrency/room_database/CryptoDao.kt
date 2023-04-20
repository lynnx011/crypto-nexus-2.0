package com.example.cryptocurrency.room_database
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cryptocurrency.model.CryptoDetails
import com.example.cryptocurrency.model.transaction.TransactionModel

@Dao
interface CryptoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCryptos(cryptos: List<CryptoDetails>)

    @Query("SELECT * FROM crypto_details")
    fun getAllCryptos() : LiveData<List<CryptoDetails>>

    @Query("DELETE FROM crypto_details")
    suspend fun deleteCryptos()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionModel)

    @Query("SELECT * FROM transaction_model")
    fun getTransaction() : LiveData<List<TransactionModel>>

    @Delete
    fun deleteTransaction(transaction: TransactionModel)

}