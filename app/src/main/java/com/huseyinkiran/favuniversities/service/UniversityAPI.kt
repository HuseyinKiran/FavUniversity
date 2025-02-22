package com.huseyinkiran.favuniversities.service

import com.huseyinkiran.favuniversities.model.dto.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UniversityAPI {

    @GET("universities-at-turkey/page-{pageNumber}.json")
    suspend fun getUniversities(@Path("pageNumber") pageNumber: Int): Response

}