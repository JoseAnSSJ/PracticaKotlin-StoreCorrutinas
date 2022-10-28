package com.example.store.Common.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.store.Common.Entity.StoreEntity

@Database(entities = arrayOf(StoreEntity::class), version = 2)
abstract class StoreDB : RoomDatabase() {

    abstract fun storeDao(): StoreDAO

}