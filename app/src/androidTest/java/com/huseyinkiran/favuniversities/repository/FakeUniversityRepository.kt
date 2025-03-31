package com.huseyinkiran.favuniversities.repository

import com.huseyinkiran.favuniversities.data.remote.dto.ProvinceDto
import com.huseyinkiran.favuniversities.data.remote.dto.Response
import com.huseyinkiran.favuniversities.domain.model.ProvinceUIModel
import com.huseyinkiran.favuniversities.domain.model.UniversityUIModel
import com.huseyinkiran.favuniversities.domain.model.toUI
import com.huseyinkiran.favuniversities.domain.repository.UniversityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeUniversityRepository : UniversityRepository {

    private val universities = mutableListOf<UniversityUIModel>()
    private val universitiesLiveData = MutableStateFlow<List<UniversityUIModel>>(universities)

    private val fakeProvinces = mutableListOf<ProvinceDto>()
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

    override suspend fun getUniversities(pageNumber: Int): List<ProvinceUIModel> {
        val response = Response(1, 1, 1, 1, 1, fakeProvinces)
        return response.data.map { it.toUI() }
    }

    private fun refreshData() {
        universitiesLiveData.value = universities
    }

    fun setFakeProvinces(provinces: List<ProvinceDto>) {
        fakeProvinces.clear()
        fakeProvinces.addAll(provinces)
    }

    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

}