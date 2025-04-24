package com.huseyinkiran.favuniversities.data.repository

import com.huseyinkiran.favuniversities.data.remote.UniversityAPI
import com.huseyinkiran.favuniversities.domain.model.CityUIModel
import com.huseyinkiran.favuniversities.domain.model.toUIModel
import com.huseyinkiran.favuniversities.domain.repository.UniversityRemoteRepository
import javax.inject.Inject

class UniversityRemoteRepositoryImpl @Inject constructor(
    private val api: UniversityAPI
) : UniversityRemoteRepository {

    override suspend fun getUniversities(pageNumber: Int): List<CityUIModel> {
        val response = api.getUniversities(pageNumber)
        return response.data.map { it.toUIModel() }
    }

}