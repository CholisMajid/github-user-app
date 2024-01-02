package com.cholis.myapplicationgithubuser.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {
    companion object {
        private const val BASE_URL = "https://api.github.com/"
        private const val GITHUB_TOKEN = "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"
        fun create(): ApiService{
            val logInterceptor = Interceptor { connect ->
                val request = connect.request()
                val reqHeaders = request.newBuilder()
                    .addHeader("Authorization", "bearer $GITHUB_TOKEN")
                    .build()
                connect.proceed(reqHeaders)
            }
            val user = OkHttpClient.Builder()
                .addInterceptor(logInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("$BASE_URL")
                .addConverterFactory(GsonConverterFactory.create())
                .client(user)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}