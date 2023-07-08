package com.example.mycasektx.data.network

import com.example.mycasektx.data.responses.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @POST("login")
    @FormUrlEncoded

    suspend fun login (
        @Field("username") username : String ,
        @Field("password") password : String
    ): LoginResponse
}