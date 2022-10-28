package com.example.store.MainModule.Model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.store.Common.Entity.StoreEntity
import com.example.store.Common.Utili.Constants
import com.example.store.Common.Utili.StoresExepcion
import com.example.store.Common.Utili.TypeError
import com.example.store.StoreAplication
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainInteractor {


    val store: LiveData<MutableList<StoreEntity>> = liveData {
           val storeLiveData = StoreAplication.database.storeDao().getAllStore()
        emitSource(storeLiveData)

    }

    suspend fun delteStore(storeEntity: StoreEntity) = withContext(Dispatchers.IO){
       val result = StoreAplication.database.storeDao().deleteStore(storeEntity)
        if (result == 0) throw StoresExepcion(TypeError.DELETE)
    }

    suspend fun updateStore(storeEntity: StoreEntity) = withContext(Dispatchers.IO){
        val result = StoreAplication.database.storeDao().updateStore(storeEntity)
        if (result == 0) throw StoresExepcion(TypeError.UPDATE)
    }
}