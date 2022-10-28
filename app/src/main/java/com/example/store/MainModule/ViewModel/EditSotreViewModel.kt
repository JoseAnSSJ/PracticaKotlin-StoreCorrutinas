package com.example.store.MainModule.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.store.Common.Entity.StoreEntity
import com.example.store.Common.Utili.Constants
import com.example.store.Common.Utili.StoresExepcion
import com.example.store.Common.Utili.TypeError
import com.example.store.MainModule.Model.EditStoreInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EditSotreViewModel: ViewModel() {

    private var storeId: Long = 0L
    private val showFab = MutableLiveData<Boolean>()
    private val result = MutableLiveData<Any>()
    private val typeError: MutableLiveData<TypeError> = MutableLiveData()


    private val interactor: EditStoreInteractor

    init {
        interactor = EditStoreInteractor()
    }

    fun setSotoreSelect(storeEntity: StoreEntity){
        storeId = storeEntity.id
    }

    fun getStoreSelect(): LiveData<StoreEntity>{
        return interactor.getIdStore(storeId)
    }

    fun setShowFab(isVisible: Boolean){
        showFab.value = isVisible
    }

    fun getShowFab(): LiveData<Boolean>{
        return showFab
    }

    fun setResult(value: Any){
        result.value = value
    }

    fun getResult(): LiveData<Any>{
        return result
    }

   fun saveStore(storeEntity: StoreEntity){

       excecuteAction(storeEntity) { interactor.saveStore(storeEntity) }
    }

   fun updateStore(storeEntity: StoreEntity){

       excecuteAction(storeEntity) { interactor.updateStore(storeEntity) }
    }

    private fun excecuteAction(storeEntity: StoreEntity, block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try{
                block()
                result.value = storeEntity
            }catch (e: StoresExepcion){
                typeError.value = e.typeError
            }
        }

    }

    fun setTypeError(typeError: TypeError){
        this.typeError.value = typeError
    }

    fun getTypeError(): MutableLiveData<TypeError> = typeError


}