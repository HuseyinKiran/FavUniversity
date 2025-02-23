package com.huseyinkiran.favuniversities.data.repository

import androidx.lifecycle.LiveData
import com.huseyinkiran.favuniversities.data.local.UniversityEntity
import com.huseyinkiran.favuniversities.data.local.UniversityDAO
import com.huseyinkiran.favuniversities.domain.repository.UniversityLocalRepository
import javax.inject.Inject

class UniversityLocalRepositoryImpl @Inject constructor(
    private val dao: UniversityDAO
) : UniversityLocalRepository {

    override suspend fun upsertUniversity(university: UniversityEntity) {
        dao.upsertUniversity(university)
    }

    override suspend fun deleteUniversity(university: UniversityEntity) {
        dao.deleteUniversity(university)
    }

    override fun getAllUniversities(): LiveData<List<UniversityEntity>> {
        return dao.getAllFavorites()
    }

    override suspend fun getUniversityByName(universityName: String): UniversityEntity? {
        return dao.getFavoriteByName(universityName)
    }

}