package com.huseyinkiran.favuniversities.data.repository

import com.huseyinkiran.favuniversities.domain.model.ProvinceUIModel
import com.huseyinkiran.favuniversities.domain.model.UniversityUIModel
import com.huseyinkiran.favuniversities.domain.repository.UniversityLocalRepository
import com.huseyinkiran.favuniversities.domain.repository.UniversityRemoteRepository
import com.huseyinkiran.favuniversities.domain.repository.UniversityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UniversityRepositoryImpl @Inject constructor(
    private val localRepository: UniversityLocalRepository,
    private val remoteRepository: UniversityRemoteRepository
) : UniversityRepository {

    override suspend fun upsertUniversity(university: UniversityUIModel) {
        localRepository.upsertUniversity(university)
    }

    override suspend fun deleteUniversity(university: UniversityUIModel) {
        localRepository.deleteUniversity(university)
    }

    override fun getAllFavorites(): Flow<List<UniversityUIModel>> {
        return localRepository.getAllFavorites()
    }

    override suspend fun getUniversityByName(universityName: String): UniversityUIModel? {
        return localRepository.getUniversityByName(universityName)
    }

    override suspend fun getUniversities(pageNumber: Int): List<ProvinceUIModel> {
        return remoteRepository.getUniversities(pageNumber)
    }

}