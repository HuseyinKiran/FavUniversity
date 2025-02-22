package com.huseyinkiran.favuniversities.repository.local

import androidx.lifecycle.LiveData
import com.huseyinkiran.favuniversities.model.dto.UniversityDto

interface UniversityLocalRepository {

    suspend fun upsertUniversity(university: UniversityDto)

    suspend fun deleteUniversity(university: UniversityDto)

    fun getAllUniversities() : LiveData<List<UniversityDto>>

    suspend fun getUniversityByName(universityName: String): UniversityDto?

}