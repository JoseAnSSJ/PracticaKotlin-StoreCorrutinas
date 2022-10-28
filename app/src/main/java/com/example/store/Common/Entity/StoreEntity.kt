package com.example.store.Common.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "StoreEntity")
data class StoreEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String,
    var phone: String,
    var webSite: String = "",
    var photoUrl: String,
    var isFavorite: Boolean = false


) {

    constructor(): this(name = "", phone = "", photoUrl = "")

}
