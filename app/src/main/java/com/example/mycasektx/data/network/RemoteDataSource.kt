package com.example.mycasektx.data.network

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RemoteDataSource @Inject constructor(){
    companion object{
        private const val BASE_URL = "https://actio.nodev.store/api/"
    }
//
//    fun <Api> buildApi(api: Class<Api> , context : Context) : Api{
//
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(api)
//    }
}