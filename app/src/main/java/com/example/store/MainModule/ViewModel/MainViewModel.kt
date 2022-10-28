package com.example.store.MainModule.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.store.Common.Entity.StoreEntity
import com.example.store.Common.Utili.Constants
import com.example.store.Common.Utili.StoresExepcion
import com.example.store.Common.Utili.TypeError
import com.example.store.MainModule.Model.MainInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private var interactor: MainInteractor

    private val typeError: MutableLiveData<TypeError> = MutableLiveData()

    init {
        interactor = MainInteractor()
    }

    private val showProgress: MutableLiveData<Boolean> = MutableLiveData()


/*    private val stores: MutableLiveData<MutableList<StoreEntity>> by lazy{
        MutableLiveData<MutableList<StoreEntity>>().also {
            loadStore()
        }
    }*/

    private val stores = interactor.store

    fun setTypeError(typeError: TypeError){
        this.typeError.value = typeError
    }

    fun getStores(): LiveData<MutableList<StoreEntity>>{
        return stores
    }

    fun showProgress(): LiveData<Boolean>{

        return showProgress
    }

 /*   private fun loadStore(){
        showProgress.value = Constants.SHOW
        interactor.getStores {
            showProgress.value = Constants.HIDEN
            stores.value = it
            storeList = it
        }
    }*/

    fun deleteStore(storeEntity: StoreEntity){
/*        viewModelScope.launch {
            interactor.delteStore(storeEntity)
        }*/
        excecuteAction { interactor.delteStore(storeEntity) }

    }
    fun updateStore(storeEntity: StoreEntity){
/*        viewModelScope.launch {
            showProgress.value = Constants.SHOW
            try{
                storeEntity.isFavorite = !storeEntity.isFavorite
                interactor.updateStore(storeEntity)
            }catch (e: Exception){
                e.printStackTrace()
            }
            finally {
                showProgress.value = Constants.HIDEN
            }
        }*/
        storeEntity.isFavorite = !storeEntity.isFavorite
        excecuteAction { interactor.updateStore(storeEntity) }
    }

    private fun excecuteAction(block: suspend () -> Unit): Job{
        return viewModelScope.launch {
            showProgress.value = Constants.SHOW
            try{
               block()
            }catch (e: StoresExepcion){
                typeError.value = e.typeError
            }
            finally {
                showProgress.value = Constants.HIDEN
            }
        }
    }


    fun getTypeError(): MutableLiveData<TypeError> = typeError
}