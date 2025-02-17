package com.huseyinkiran.favuniversities.repository.local

import androidx.lifecycle.LiveData
import com.huseyinkiran.favuniversities.model.University

interface UniversityLocalRepository {

    suspend fun upsertUniversity(university: University)

    suspend fun deleteUniversity(university: University)

    fun getAllUniversities() : LiveData<List<University>>

    suspend fun getUniversityByName(universityName: String): University?

}