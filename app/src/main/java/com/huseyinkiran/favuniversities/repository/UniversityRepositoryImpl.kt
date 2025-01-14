package com.huseyinkiran.favuniversities.repository

import androidx.lifecycle.LiveData
import com.huseyinkiran.favuniversities.model.Province
import com.huseyinkiran.favuniversities.model.Response
import com.huseyinkiran.favuniversities.model.University
import com.huseyinkiran.favuniversities.room.UniversityDAO
import com.huseyinkiran.favuniversities.service.UniversityAPI
import javax.inject.Inject

class UniversityRepositoryImpl @Inject constructor(
    private val universityDAO: UniversityDAO,
    private val universityAPI: UniversityAPI
) : UniversityRepository {

    override suspend fun upsertUniversity(university: University) {
        universityDAO.upsertUniversity(university)
    }

    override suspend fun deleteUniversity(university: University) {
        universityDAO.deleteUniversity(university)
    }

    override fun getAllUniversities(): LiveData<List<University>> {
        return universityDAO.getAllFavorites()
    }

    override suspend fun getProvinces(pageNumber: Int): Response {
        return universityAPI.getUniversities(pageNumber)
    }

    override suspend fun getUniversityByName(universityName: String): University? {
        return universityDAO.getFavoriteByName(universityName)
    }

    override suspend fun isUniversityFavorite(universityName: String?): Boolean {
        if (universityName == null) return false
        return universityDAO.getFavoriteByName(universityName) != null
    }

}