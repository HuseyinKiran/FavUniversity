package com.huseyinkiran.favuniversities.repository

import androidx.lifecycle.LiveData
import com.huseyinkiran.favuniversities.model.dto.Response
import com.huseyinkiran.favuniversities.model.dto.UniversityDto
import com.huseyinkiran.favuniversities.repository.local.UniversityLocalRepository
import com.huseyinkiran.favuniversities.repository.remote.UniversityRemoteRepository
import javax.inject.Inject

class UniversityRepositoryImpl @Inject constructor(
    private val localRepository: UniversityLocalRepository,
    private val remoteRepository: UniversityRemoteRepository
) : UniversityRepository {

    override suspend fun upsertUniversity(university: UniversityDto) {
        localRepository.upsertUniversity(university)
    }

    override suspend fun deleteUniversity(university: UniversityDto) {
        localRepository.deleteUniversity(university)
    }

    override fun getAllUniversities(): LiveData<List<UniversityDto>> {
        return localRepository.getAllUniversities()
    }

    override suspend fun getUniversityByName(universityName: String): UniversityDto? {
        return localRepository.getUniversityByName(universityName)
    }

    override suspend fun getProvinces(pageNumber: Int): Response {
        return remoteRepository.getProvinces(pageNumber)
    }

}