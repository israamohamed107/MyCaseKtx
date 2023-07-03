package com.example.mycasektx.data.repo

import com.example.mycasektx.data.network.AuthApi
import javax.inject.Inject

class AuthRepository @Inject constructor(
    val api: AuthApi
) : BaseRepository() {

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {  api.login(email, password)}


}