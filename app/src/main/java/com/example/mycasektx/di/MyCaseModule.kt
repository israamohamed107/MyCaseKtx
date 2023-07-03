package com.example.mycasektx.di

import com.example.mycasektx.data.network.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

var token = "\"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiMzg0ZTYyZTg5YzNhZTUwN2Y0NTI0ZDViZGVmZjA2ZGY5YTRjNDI5MjAxOTc5NDI4MDQyODNjMDNkNzkwZmVmMzZhMDQwNGE3MzczN2E4MTIiLCJpYXQiOjE2ODgzMDE1MjEuNzk4MjU4LCJuYmYiOjE2ODgzMDE1MjEuNzk4MjY3LCJleHAiOjE3MTk5MjM5MjEuNzg4MTE2LCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.mb3ffgEvTZ74nC_--Sj9oEM866tAMm7dH_sYNMiGZh6FhGEzvY0jFRUJw8Qn77kAxsLN82GxxaPn5IwMTFyLhKyj3jAdDcGMuBOZ98pNhWJIGwYs0BU_TeVXX0ObO2yfGMsf64pIcp6tH-wxPyFT2A2rTKcYErvyXd2CdqpiJz5BVePzswu7W8CxKt1y83bpxbJOKGfOVimhPc6QUD_oa8RjnAcWgfvDDjwjSjfApUx-JOWwvvYUdCjReTaoVh_LjFxmswEROaX6lniV0dddY1cqGHA_pgneGGcZdEHT_GEeRVuszBFJPVylUAtmrYRU31OMCLZ10g7Ce3aRJQ1eS1gPYNH7ZnxRLOJ2wKWr2CfdG-qzJRWuRb3wLi-679yHNyTExpBEj2NtZ5twCKGW5IYYNdHgaQ3gPCn6mBL7TrHAcWk3_P4wrFApOKSrNMW41f-B4mB9yaJ6WXe8k1tFocF_crt1CC8i8Up6YQSd0bwry0XN4a1GHWNBizJdnvu0cVwSNn7Ar5jaG8XsKOgAA-Z12rQNpqaxCoCy2vA8_v4sGRhAIVlBw0M15teZQrGHQeVjl79PBvxR11pY5uedijWsqs-qeB2DkPBVg9AjLl-DeoUlYkxeE-cz9W_LBbprXEqyHhDVLgNelVm8ioSEiw4x9RCtkpvfvFHS99H9GI4"
@Module
@InstallIn(SingletonComponent::class)
class MyCaseModule {

    @Provides
    @Singleton
    fun getOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(50, TimeUnit.SECONDS)
        .writeTimeout(150, TimeUnit.SECONDS)
        .readTimeout(50, TimeUnit.SECONDS)
        .callTimeout(50, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(Interceptor { chain ->
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url
            val url = originalUrl.newBuilder().build()
            val requestBuilder = originalRequest.newBuilder().url(url)
                .addHeader("Accept", "application/json")

                    val request = requestBuilder . build ()
            val response = chain.proceed(request)
            response.code//status code
            response
        })
        .build()

    @Provides
    @Singleton
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://actio.nodev.store/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideAuthApi(
        retrofit: Retrofit
    ): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }


}