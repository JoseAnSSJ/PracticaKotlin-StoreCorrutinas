package com.example.store.Common.DataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.store.Common.Entity.StoreEntity

@Dao
interface StoreDAO {
    @Query("Select * from StoreEntity")
    fun getAllStore(): LiveData<MutableList<StoreEntity>>

    @Insert
    suspend fun addStore(storeEntity: StoreEntity) : Long

    @Update
    suspend fun updateStore(storeEntity: StoreEntity): Int

    @Delete
    suspend fun deleteStore(storeEntity: StoreEntity): Int

    @Query("Select * from StoreEntity where id = :id")
    fun getStoreById(id: Long): LiveData<StoreEntity>

}