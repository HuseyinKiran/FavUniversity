package com.huseyinkiran.turkishuniversities.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://storage.googleapis.com/invio-com/usg-challenge/"

    val api: UniversityAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UniversityAPI::class.java)
    }

}

