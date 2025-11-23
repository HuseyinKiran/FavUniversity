package com.huseyinkiran.favuniversities.data.remote

import com.huseyinkiran.favuniversities.data.remote.dto.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UniversityAPI {

    @GET("api/provinces")
    suspend fun getUniversities(
        @Query("page") pageNumber: Int,
        @Query("pageSize") pageSize: Int = 30
    ): Response

}