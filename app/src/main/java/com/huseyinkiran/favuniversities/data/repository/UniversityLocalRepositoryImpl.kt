package com.huseyinkiran.favuniversities.data.repository

import com.huseyinkiran.favuniversities.data.local.UniversityDAO
import com.huseyinkiran.favuniversities.data.mapper.toEntity
import com.huseyinkiran.favuniversities.data.mapper.toUniversity
import com.huseyinkiran.favuniversities.domain.model.University
import com.huseyinkiran.favuniversities.domain.repository.UniversityLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UniversityLocalRepositoryImpl @Inject constructor(
    private val dao: UniversityDAO
) : UniversityLocalRepository {

    override suspend fun upsertUniversity(university: University) {
        dao.upsertUniversity(university.toEntity())
    }

    override suspend fun deleteUniversity(university: University) {
        dao.deleteUniversity(university.toEntity())
    }

    override fun getAllFavorites(): Flow<List<University>> {
        return dao.getAllFavorites().map { list ->
            list.map { it.toUniversity() }
        }
    }

    override suspend fun getUniversityByName(universityName: String): University? {
        return dao.getFavoriteByName(universityName)?.toUniversity()
    }

}