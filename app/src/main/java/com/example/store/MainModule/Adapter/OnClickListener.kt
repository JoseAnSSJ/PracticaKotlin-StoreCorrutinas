package com.example.store.MainModule.Adapter

import com.example.store.Common.Entity.StoreEntity

interface OnClickListener {

    fun onClick(storeEntity: StoreEntity)
    fun onFavoriteStore(storeEntity: StoreEntity)
    fun onDeleteStore(storeEntity: StoreEntity)
}