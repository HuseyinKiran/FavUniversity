package com.huseyinkiran.favuniversities.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.huseyinkiran.favuniversities.data.paging.CityPagingSource
import com.huseyinkiran.favuniversities.data.paging.CityPagingSource.Companion.NETWORK_PAGE_SIZE
import com.huseyinkiran.favuniversities.domain.model.City
import com.huseyinkiran.favuniversities.domain.model.University
import com.huseyinkiran.favuniversities.domain.repository.UniversityLocalRepository
import com.huseyinkiran.favuniversities.domain.repository.UniversityRemoteRepository
import com.huseyinkiran.favuniversities.domain.repository.UniversityRepository
import kotlinx.coroutines.flow.Flow
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

    override fun getAllFavorites(): Flow<List<University>> {
        return localRepository.getAllFavorites()
    }

    override suspend fun getUniversityByName(universityName: String): University? {
        return localRepository.getUniversityByName(universityName)
    }

    override suspend fun getCities(pageNumber: Int): List<City> {
        return remoteRepository.getCities(pageNumber)
    }

    override fun getCityPagingFlow(): Flow<PagingData<City>> {
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