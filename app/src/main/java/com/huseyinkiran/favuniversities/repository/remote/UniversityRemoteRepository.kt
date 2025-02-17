package com.huseyinkiran.favuniversities.repository.remote

import com.huseyinkiran.favuniversities.model.Response

interface UniversityRemoteRepository {

    suspend fun getProvinces(pageNumber: Int) : Response

}