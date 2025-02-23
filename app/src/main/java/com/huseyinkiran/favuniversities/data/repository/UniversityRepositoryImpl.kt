package com.huseyinkiran.favuniversities.data.repository

import androidx.lifecycle.LiveData
import com.huseyinkiran.favuniversities.data.remote.dto.Response
import com.huseyinkiran.favuniversities.data.local.UniversityEntity
import com.huseyinkiran.favuniversities.domain.repository.UniversityLocalRepository
import com.huseyinkiran.favuniversities.domain.repository.UniversityRemoteRepository
import com.huseyinkiran.favuniversities.domain.repository.UniversityRepository
import javax.inject.Inject

class UniversityRepositoryImpl @Inject constructor(
    private val localRepository: UniversityLocalRepository,
    private val remoteRepository: UniversityRemoteRepository
) : UniversityRepository {

    override suspend fun upsertUniversity(university: UniversityEntity) {
        localRepository.upsertUniversity(university)
    }

    override suspend fun deleteUniversity(university: UniversityEntity) {
        localRepository.deleteUniversity(university)
    }

    override fun getAllUniversities(): LiveData<List<UniversityEntity>> {
        return localRepository.getAllUniversities()
    }

    override suspend fun getUniversityByName(universityName: String): UniversityEntity? {
        return localRepository.getUniversityByName(universityName)
    }

    override suspend fun getProvinces(pageNumber: Int): Response {
        return remoteRepository.getProvinces(pageNumber)
    }

}