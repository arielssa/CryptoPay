package com.qrypta.cryptopay.api

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val token: String?,
    val message: String?
)
