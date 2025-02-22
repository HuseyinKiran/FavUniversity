package com.huseyinkiran.favuniversities.repository

import androidx.lifecycle.LiveData
import com.huseyinkiran.favuniversities.model.dto.Response
import com.huseyinkiran.favuniversities.model.dto.UniversityDto

interface UniversityRepository {

    suspend fun upsertUniversity(university: UniversityDto)

    suspend fun deleteUniversity(university: UniversityDto)

    fun getAllUniversities() : LiveData<List<UniversityDto>>

    suspend fun getUniversityByName(universityName: String): UniversityDto?

    suspend fun getProvinces(pageNumber: Int) : Response

}