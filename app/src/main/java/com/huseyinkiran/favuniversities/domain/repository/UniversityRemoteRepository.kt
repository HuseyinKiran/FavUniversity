package com.huseyinkiran.favuniversities.domain.repository

import com.huseyinkiran.favuniversities.data.remote.dto.Response

interface UniversityRemoteRepository {

    suspend fun getProvinces(pageNumber: Int) : Response

}