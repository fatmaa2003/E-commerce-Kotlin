package com.example.e_commercekotlin.data

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://e-commerce-production-e59d.up.railway.app/api/"

    private class AuthInterceptor(private val token: String) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
//                .addHeader("Authorization", "Bearer $token")
                .build()
            return chain.proceed(request)
        }
    }

    private fun createOkHttpClient(token: String): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(token))
            .build()
    }

    fun createRetrofit(token: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(token))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        createRetrofit("eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiQjZBUkJ4UnlOS2RCalpCNW1YMDJ5Zz09IiwidXNlcm5hbWUiOiJoYW5hbjAzIiwic3ViIjoiNTUiLCJpYXQiOjE3MjU0NTIxMzYsImV4cCI6MTcyNTQ4ODEzNn0.C5zhzT96qCpUWjUDtczIJYAS1uVpZQq3NQ_MWlW44XzVVTvdQ-MlY0B2d1bxRtaJQzIfPeMaTmT0JdVlerxjQg").create(ApiService::class.java)
    }
}

