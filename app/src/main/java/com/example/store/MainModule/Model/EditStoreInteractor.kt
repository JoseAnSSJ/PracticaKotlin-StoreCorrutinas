package com.example.store.MainModule.Model

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import com.example.store.Common.Entity.StoreEntity
import com.example.store.Common.Utili.StoresExepcion
import com.example.store.Common.Utili.TypeError
import com.example.store.StoreAplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EditStoreInteractor {
   suspend fun saveStore(storeEntity: StoreEntity) = withContext(Dispatchers.IO){
       try{
           StoreAplication.database.storeDao().addStore(storeEntity)
       }catch (e: SQLiteConstraintException){
           StoresExepcion(TypeError.INSERT)
       }
    }

   suspend fun updateStore(storeEntity: StoreEntity) = withContext(Dispatchers.IO){
       try{
           StoreAplication.database.storeDao().updateStore(storeEntity)
       }catch (e: SQLiteConstraintException){
           StoresExepcion(TypeError.UPDATE)
       }
    }

    fun getIdStore(id: Long): LiveData<StoreEntity>{
        return StoreAplication.database.storeDao().getStoreById(id)
    }
}