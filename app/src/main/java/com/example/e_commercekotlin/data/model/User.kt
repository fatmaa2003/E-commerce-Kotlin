package com.example.e_commercekotlin.data.model

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val token: String, val role: String)

data class SignupResponse(val message: String)

