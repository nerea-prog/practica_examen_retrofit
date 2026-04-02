package com.example.practica_examen.network

import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

class RetrofitClient {
    companion object {
        private var mItemAPI: ApiService? = null


        @Synchronized
        fun API(): ApiService {
            if (mItemAPI == null) {


                val gsondateformat = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create()

                Log.d("RetrofitClient", "Viendo conexión")
                mItemAPI = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gsondateformat))
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .build()
                    .create(ApiService::class.java)
                Log.d("RetrofitClient", "URL correcta")
            }
            return mItemAPI!!
        }
    }
}