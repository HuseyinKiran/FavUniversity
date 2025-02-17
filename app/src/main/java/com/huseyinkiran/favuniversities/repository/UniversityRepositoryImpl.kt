package com.huseyinkiran.favuniversities.repository

import androidx.lifecycle.LiveData
import com.huseyinkiran.favuniversities.model.Response
import com.huseyinkiran.favuniversities.model.University
import com.huseyinkiran.favuniversities.repository.local.UniversityLocalRepository
import com.huseyinkiran.favuniversities.repository.remote.UniversityRemoteRepository
import javax.inject.Inject

class UniversityRepositoryImpl @Inject constructor(
    private val localRepository: UniversityLocalRepository,
    private val remoteRepository: UniversityRemoteRepository
) : UniversityRepository {

    override suspend fun upsertUniversity(university: University) {
        localRepository.upsertUniversity(university)
    }

    override suspend fun deleteUniversity(university: University) {
        localRepository.deleteUniversity(university)
    }

    override fun getAllUniversities(): LiveData<List<University>> {
        return localRepository.getAllUniversities()
    }

    override suspend fun getUniversityByName(universityName: String): University? {
        return localRepository.getUniversityByName(universityName)
    }

    override suspend fun getProvinces(pageNumber: Int): Response {
        return remoteRepository.getProvinces(pageNumber)
    }

}