package com.huseyinkiran.favuniversities.repository

import androidx.lifecycle.LiveData
import com.huseyinkiran.favuniversities.model.Response
import com.huseyinkiran.favuniversities.model.University

interface UniversityRepository {

    suspend fun upsertUniversity(university: University)

    suspend fun deleteUniversity(university: University)

    fun getAllUniversities() : LiveData<List<University>>

    suspend fun getUniversityByName(universityName: String): University?

    suspend fun getProvinces(pageNumber: Int) : Response

}