package com.huseyinkiran.favuniversities.repository


import com.huseyinkiran.favuniversities.data.remote.dto.CityDto
import com.huseyinkiran.favuniversities.data.remote.dto.Response
import com.huseyinkiran.favuniversities.domain.model.City
import com.huseyinkiran.favuniversities.domain.model.University
import com.huseyinkiran.favuniversities.domain.model.toUniversity
import com.huseyinkiran.favuniversities.domain.repository.UniversityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeUniversityRepository : UniversityRepository {

    private val universities = mutableListOf<University>()
    private val universitiesLiveData = MutableStateFlow<List<University>>(universities)

    private val fakeCities = mutableListOf<CityDto>()
    private var shouldReturnError = false

    override suspend fun upsertUniversity(university: University) {
        universities.add(university)
    }

    override suspend fun deleteUniversity(university: University) {
        universities.remove(university)
        refreshData()
    }

    override fun getAllFavorites(): Flow<List<University>> {
        return universitiesLiveData
    }

    override suspend fun getUniversityByName(universityName: String): University? {
        return universities.firstOrNull { it.name == universityName }
    }

    override suspend fun getCities(pageNumber: Int): List<City> {
        val response = Response(1,1,1,1,1,fakeCities)
        return response.data.map { it.toUniversity() }
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