package com.huseyinkiran.turkishuniversities.service

import com.huseyinkiran.turkishuniversities.model.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UniversityAPI {

    @GET("universities-at-turkey/page-{pageNumber}.json")
    suspend fun getUniversities(@Path("pageNumber") pageNumber: Int): Response

}

