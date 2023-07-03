package com.example.mycasektx.data.network

import okhttp3.ResponseBody

sealed class Resource<out T>{

    data class Success<out T> (val value : T): Resource<T>()
    data class Failure<out T> (val error: String): Resource<T>()

//    object Loading : Resource<Nothing>()
}
