package com.huseyinkiran.favuniversities.repository.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.huseyinkiran.favuniversities.model.University
import com.huseyinkiran.favuniversities.room.UniversityDAO
import javax.inject.Inject

class UniversityLocalRepositoryImpl @Inject constructor(
    private val dao: UniversityDAO
) : UniversityLocalRepository {

    override suspend fun upsertUniversity(university: University) {
        dao.upsertUniversity(university)
    }

    override suspend fun deleteUniversity(university: University) {
        dao.deleteUniversity(university)
    }

    override fun getAllUniversities(): LiveData<List<University>> {
        return dao.getAllFavorites()
    }

    override suspend fun getUniversityByName(universityName: String): University? {
        return dao.getFavoriteByName(universityName)
    }

}