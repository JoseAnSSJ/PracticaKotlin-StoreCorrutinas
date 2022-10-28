package com.example.store

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.store.Common.DataBase.StoreDB
import com.example.store.Common.StoreApi

class StoreAplication: Application() {

    companion object{
        lateinit var database: StoreDB
        lateinit var storeApi: StoreApi
    }

    override fun onCreate() {
        super.onCreate()

        val MIGRATION_1_2 = object : Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE StoreEntity ADD COLUMN photoUrl TEXT NOT NULL DEFAULT ''")
            }
        }

        database = Room.databaseBuilder(this, StoreDB::class.java,"StoreDB")
            .addMigrations(MIGRATION_1_2)
            .build()

        storeApi = StoreApi.getInstant(this)

    }

}