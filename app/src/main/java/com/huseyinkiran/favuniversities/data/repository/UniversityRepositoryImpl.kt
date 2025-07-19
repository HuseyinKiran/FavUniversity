package com.huseyinkiran.favuniversities.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.huseyinkiran.favuniversities.data.paging.CityPagingSource
import com.huseyinkiran.favuniversities.data.paging.CityPagingSource.Companion.NETWORK_PAGE_SIZE
import com.huseyinkiran.favuniversities.domain.model.CityUIModel
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

    override suspend fun getUniversities(pageNumber: Int): List<CityUIModel> {
        return remoteRepository.getUniversities(pageNumber)
    }

    override fun getCityPagingFlow(): Flow<PagingData<CityUIModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                prefetchDistance = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CityPagingSource(this) }
        ).flow
    }

}