package com.huseyinkiran.favuniversities.domain.repository

import androidx.lifecycle.LiveData
import com.huseyinkiran.favuniversities.data.remote.dto.Response
import com.huseyinkiran.favuniversities.data.local.UniversityEntity

interface UniversityRepository {

    suspend fun upsertUniversity(university: UniversityEntity)

    suspend fun deleteUniversity(university: UniversityEntity)

    fun getAllUniversities() : LiveData<List<UniversityEntity>>

    suspend fun getUniversityByName(universityName: String): UniversityEntity?

    suspend fun getProvinces(pageNumber: Int) : Response

}