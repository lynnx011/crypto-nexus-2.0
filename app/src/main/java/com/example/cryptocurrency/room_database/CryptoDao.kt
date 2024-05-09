package com.example.cryptocurrency.room_database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cryptocurrency.domain.model.CryptoDetails
import com.example.cryptocurrency.domain.model.Transaction


@Dao
interface CryptoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCryptos(cryptos: List<CryptoDetails>)

    @Query("SELECT * FROM crypto_details")
    fun getAllCryptos() : LiveData<List<CryptoDetails>>

    @Query("DELETE FROM crypto_details")
    suspend fun deleteCryptos()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Query("SELECT * FROM transaction_model")
    fun getTransaction() : LiveData<List<Transaction>>

    @Query("SELECT * FROM transaction_model WHERE id = :id")
    suspend fun getTransactionByID(id: String): Transaction

    @Delete
    fun deleteTransaction(transaction: Transaction)

}