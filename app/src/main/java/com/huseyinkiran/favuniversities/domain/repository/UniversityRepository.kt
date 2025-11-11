package com.huseyinkiran.favuniversities.domain.repository

import androidx.paging.PagingData
import com.huseyinkiran.favuniversities.domain.model.City
import com.huseyinkiran.favuniversities.domain.model.University
import kotlinx.coroutines.flow.Flow

interface UniversityRepository {

    suspend fun upsertUniversity(university: University)

    suspend fun deleteUniversity(university: University)

    fun getAllFavorites() : Flow<List<University>>

    suspend fun getUniversityById(universityId: Int): University?

    suspend fun getCities(pageNumber: Int) : List<City>

    fun getCityPagingFlow(): Flow<PagingData<City>>

}