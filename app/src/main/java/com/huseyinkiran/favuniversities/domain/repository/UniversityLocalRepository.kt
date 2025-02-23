package com.huseyinkiran.favuniversities.domain.repository

import androidx.lifecycle.LiveData
import com.huseyinkiran.favuniversities.data.local.UniversityEntity

interface UniversityLocalRepository {

    suspend fun upsertUniversity(university: UniversityEntity)

    suspend fun deleteUniversity(university: UniversityEntity)

    fun getAllUniversities() : LiveData<List<UniversityEntity>>

    suspend fun getUniversityByName(universityName: String): UniversityEntity?

}