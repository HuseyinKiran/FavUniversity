package com.huseyinkiran.turkishuniversities.repository

import androidx.lifecycle.LiveData
import com.huseyinkiran.turkishuniversities.data.UniversityDatabase
import com.huseyinkiran.turkishuniversities.model.Response
import com.huseyinkiran.turkishuniversities.model.University
import com.huseyinkiran.turkishuniversities.service.RetrofitInstance

class UniversityRepository(
    private val db: UniversityDatabase
) {

    private val api = RetrofitInstance.api

    suspend fun getProvinces(pageNumber: Int): Response {
        return api.getUniversities(pageNumber)
    }

    suspend fun isUniversityFavorite(universityName: String?): Boolean {
        if (universityName == null) return false
        return db.getUniversityDao().getFavoriteByName(universityName) != null
    }

    suspend fun upsertUniversity(university: University) {
        db.getUniversityDao().upsertUniversity(university)
    }

    fun getFavoriteUniversities(): LiveData<List<University>> {
        return db.getUniversityDao().getAllFavorites()
    }

    suspend fun deleteUniversity(university: University) {
        db.getUniversityDao().deleteUniversity(university)
    }

}

