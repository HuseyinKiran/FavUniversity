package com.huseyinkiran.favuniversities.domain.repository

import com.huseyinkiran.favuniversities.domain.model.UniversityUIModel
import kotlinx.coroutines.flow.Flow

interface UniversityLocalRepository {

    suspend fun upsertUniversity(university: UniversityUIModel)

    suspend fun deleteUniversity(university: UniversityUIModel)

    fun getAllFavorites() : Flow<List<UniversityUIModel>>

    suspend fun getUniversityByName(universityName: String): UniversityUIModel?

}