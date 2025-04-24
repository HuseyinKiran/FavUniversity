package com.huseyinkiran.favuniversities.repository

import com.huseyinkiran.favuniversities.data.remote.dto.CityDto
import com.huseyinkiran.favuniversities.data.remote.dto.Response
import com.huseyinkiran.favuniversities.domain.model.CityUIModel
import com.huseyinkiran.favuniversities.domain.model.UniversityUIModel
import com.huseyinkiran.favuniversities.domain.model.toUIModel
import com.huseyinkiran.favuniversities.domain.repository.UniversityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeUniversityRepository : UniversityRepository {

    private val universities = mutableListOf<UniversityUIModel>()
    private val universitiesLiveData = MutableStateFlow<List<UniversityUIModel>>(universities)

    private val fakeCities = mutableListOf<CityDto>()
    private var shouldReturnError = false

    override suspend fun upsertUniversity(university: UniversityUIModel) {
        universities.add(university)
        refreshData()
    }

    override suspend fun deleteUniversity(university: UniversityUIModel) {
        universities.remove(university)
        refreshData()
    }

    override fun getAllFavorites(): Flow<List<UniversityUIModel>> {
        return universitiesLiveData
    }

    override suspend fun getUniversityByName(universityName: String): UniversityUIModel? {
        return universities.firstOrNull { it.name == universityName }
    }

    override suspend fun getUniversities(pageNumber: Int): List<CityUIModel> {
        val response = Response(1, 1, 1, 1, 1, fakeCities)
        return response.data.map { it.toUIModel() }
    }

    private fun refreshData() {
        universitiesLiveData.value = universities
    }

    fun setFakeCities(cities: List<CityDto>) {
        fakeCities.clear()
        fakeCities.addAll(cities)
    }

    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

}