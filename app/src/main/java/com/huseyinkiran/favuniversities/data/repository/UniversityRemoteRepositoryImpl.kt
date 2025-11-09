package com.huseyinkiran.favuniversities.data.repository

import com.huseyinkiran.favuniversities.data.mapper.toCity
import com.huseyinkiran.favuniversities.data.remote.UniversityAPI
import com.huseyinkiran.favuniversities.domain.model.City
import com.huseyinkiran.favuniversities.domain.repository.UniversityRemoteRepository
import javax.inject.Inject

class UniversityRemoteRepositoryImpl @Inject constructor(
    private val api: UniversityAPI
) : UniversityRemoteRepository {

    override suspend fun getCities(pageNumber: Int): List<City> {
        val response = api.getUniversities(pageNumber)
        return response.data.map { it.toCity() }
    }

}