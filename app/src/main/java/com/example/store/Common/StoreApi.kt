package com.example.store.Common

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import java.time.Instant

class StoreApi constructor(context: Context) {

    companion object{
        @Volatile
        private var INSTANT: StoreApi? = null

        fun getInstant(context: Context) = INSTANT?: synchronized(this){
            INSTANT ?: StoreApi(context).also { INSTANT = it }
        }
    }



    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(request: Request<T>){
        requestQueue.add(request)
    }
}