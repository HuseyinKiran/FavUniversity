package com.huseyinkiran.favuniversities.repository.remote

import com.huseyinkiran.favuniversities.model.dto.Response
import com.huseyinkiran.favuniversities.service.UniversityAPI
import javax.inject.Inject

class UniversityRemoteRepositoryImpl @Inject constructor(
    private val api: UniversityAPI
) : UniversityRemoteRepository {

    override suspend fun getProvinces(pageNumber: Int): Response {
        return api.getUniversities(pageNumber)
    }

}