package com.qrypta.cryptopay.api

data class RegisterRequest(
    val full_name: String,
    val username: String,
    val email: String,
    val phone: String,
    val password: String,
)

data class RegisterResponse(
    val message: String
)
