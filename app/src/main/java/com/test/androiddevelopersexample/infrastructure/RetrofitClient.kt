package com.test.androiddevelopersexample.infrastructure

import com.test.androiddevelopersexample.domain.RetrofitService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private fun getClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: RetrofitService = getClient().create(RetrofitService::class.java)

    private fun getLoggingIntercepter() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private fun getOkHttpClient() =
        OkHttpClient.Builder().addInterceptor(getLoggingIntercepter()).build()
}