package com.example.e_commercekotlin.data

import com.example.e_commercekotlin.data.remote.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private const val BASE_URL = "https://e-commerce-production-4712.up.railway.app/api/"

    private fun createOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val original = chain.request()
                val requestBuilder: Request.Builder = original.newBuilder()
                    .header("Authorization", "Bearer " + (SharedPreferencesHelper.getToken() ?: ""))
                    .method(original.method, original.body)
                val request: Request = requestBuilder.build()
                val response = chain.proceed(request)
                response
            })
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        createRetrofit().create(ApiService::class.java)

    }
}




