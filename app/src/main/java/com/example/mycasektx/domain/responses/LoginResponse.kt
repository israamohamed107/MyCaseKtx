package com.example.mycasektx.domain.responses

data class LoginResponse(
    val token: String,
    val user: User
)