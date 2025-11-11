package com.huseyinkiran.favuniversities.domain.repository

import com.huseyinkiran.favuniversities.domain.model.University
import kotlinx.coroutines.flow.Flow

interface UniversityLocalRepository {

    suspend fun upsertUniversity(university: University)

    suspend fun deleteUniversity(university: University)

    fun getAllFavorites() : Flow<List<University>>

    suspend fun getUniversityById(universityId: Int): University?

}