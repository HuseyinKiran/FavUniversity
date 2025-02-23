package com.huseyinkiran.favuniversities.data.repository

import com.huseyinkiran.favuniversities.data.remote.dto.Response
import com.huseyinkiran.favuniversities.data.remote.UniversityAPI
import com.huseyinkiran.favuniversities.domain.repository.UniversityRemoteRepository
import javax.inject.Inject

class UniversityRemoteRepositoryImpl @Inject constructor(
    private val api: UniversityAPI
) : UniversityRemoteRepository {

    override suspend fun getProvinces(pageNumber: Int): Response {
        return api.getUniversities(pageNumber)
    }

}