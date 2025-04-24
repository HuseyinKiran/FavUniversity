package com.huseyinkiran.favuniversities.data.repository

import com.huseyinkiran.favuniversities.data.local.UniversityDAO
import com.huseyinkiran.favuniversities.domain.model.UniversityUIModel
import com.huseyinkiran.favuniversities.domain.model.toEntity
import com.huseyinkiran.favuniversities.domain.model.toUIModel
import com.huseyinkiran.favuniversities.domain.repository.UniversityLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UniversityLocalRepositoryImpl @Inject constructor(
    private val dao: UniversityDAO
) : UniversityLocalRepository {

    override suspend fun upsertUniversity(university: UniversityUIModel) {
        dao.upsertUniversity(university.toEntity())
    }

    override suspend fun deleteUniversity(university: UniversityUIModel) {
        dao.deleteUniversity(university.toEntity())
    }

    override fun getAllFavorites(): Flow<List<UniversityUIModel>> {
        return dao.getAllFavorites().map { list ->
            list.map { it.toUIModel() }
        }
    }

    override suspend fun getUniversityByName(universityName: String): UniversityUIModel? {
        return dao.getFavoriteByName(universityName)?.toUIModel()
    }

}