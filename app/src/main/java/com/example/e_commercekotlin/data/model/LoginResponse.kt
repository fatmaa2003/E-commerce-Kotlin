package com.example.e_commercekotlin.data.model


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status")
    val status: Status?,
    @SerializedName("userDetails")
    val userDetails: UserDetails?
) {
    data class Status(
        @SerializedName("description")
        val description: String?,
        @SerializedName("statusCode")
        val statusCode: Int?
    )

    data class UserDetails(
        @SerializedName("email")
        val email: String?,
        @SerializedName("firstName")
        val firstName: String?,
        @SerializedName("lastName")
        val lastName: String?,
        @SerializedName("role")
        val role: String?,
        @SerializedName("token")
        val token: String?,
        @SerializedName("username")
        val username: String?
    )
}