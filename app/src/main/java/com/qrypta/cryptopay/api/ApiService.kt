package com.qrypta.cryptopay.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")  // Cambia la ruta según tu API
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @POST("register")  // Ejemplo para registro
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>
}
