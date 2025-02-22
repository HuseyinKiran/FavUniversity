package com.huseyinkiran.favuniversities.repository.local

import androidx.lifecycle.LiveData
import com.huseyinkiran.favuniversities.model.dto.UniversityDto
import com.huseyinkiran.favuniversities.room.UniversityDAO
import javax.inject.Inject

class UniversityLocalRepositoryImpl @Inject constructor(
    private val dao: UniversityDAO
) : UniversityLocalRepository {

    override suspend fun upsertUniversity(university: UniversityDto) {
        dao.upsertUniversity(university)
    }

    override suspend fun deleteUniversity(university: UniversityDto) {
        dao.deleteUniversity(university)
    }

    override fun getAllUniversities(): LiveData<List<UniversityDto>> {
        return dao.getAllFavorites()
    }

    override suspend fun getUniversityByName(universityName: String): UniversityDto? {
        return dao.getFavoriteByName(universityName)
    }

}