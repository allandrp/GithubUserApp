package com.example.githubuserapp.data.api

import com.example.githubuserapp.BuildConfig
import com.google.gson.internal.GsonBuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {


    companion object {
        private const val baseUrl = "https://api.github.com/"
        fun initApiService(): ApiService {
            val authInterceptor = Interceptor { chain ->
                val req = chain.request()
                val reqHead = req.newBuilder().addHeader("Authorization", BuildConfig.TOKEN_API).build()

                chain.proceed(reqHead)
            }

            val httpClient = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(authInterceptor).addInterceptor(httpClient).build()

            val retrofit =
                Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
                    .client(client).build()

            return retrofit.create(ApiService::class.java)
        }
    }
}