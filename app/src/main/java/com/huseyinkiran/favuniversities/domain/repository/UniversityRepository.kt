package com.huseyinkiran.favuniversities.domain.repository

import androidx.paging.PagingData
import com.huseyinkiran.favuniversities.domain.model.CityUIModel
import com.huseyinkiran.favuniversities.domain.model.UniversityUIModel
import kotlinx.coroutines.flow.Flow

interface UniversityRepository {

    suspend fun upsertUniversity(university: UniversityUIModel)

    suspend fun deleteUniversity(university: UniversityUIModel)

    fun getAllFavorites() : Flow<List<UniversityUIModel>>

    suspend fun getUniversityByName(universityName: String): UniversityUIModel?

    suspend fun getUniversities(pageNumber: Int) : List<CityUIModel>

    fun getCityPagingFlow(): Flow<PagingData<CityUIModel>>

}