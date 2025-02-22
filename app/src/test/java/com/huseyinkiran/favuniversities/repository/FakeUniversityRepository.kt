package com.huseyinkiran.favuniversities.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.huseyinkiran.favuniversities.model.dto.ProvinceDto
import com.huseyinkiran.favuniversities.model.dto.Response
import com.huseyinkiran.favuniversities.model.dto.UniversityDto

class FakeUniversityRepository : UniversityRepository {

    private val universities = mutableListOf<UniversityDto>()
    private val universitiesLiveData = MutableLiveData<List<UniversityDto>>(universities)

    private val fakeProvinces = mutableListOf<ProvinceDto>()
    private var shouldReturnError = false

    override suspend fun upsertUniversity(university: UniversityDto) {
        universities.add(university)
        refreshData()
    }

    override suspend fun deleteUniversity(university: UniversityDto) {
        universities.remove(university)
        refreshData()
    }

    override fun getAllUniversities(): LiveData<List<UniversityDto>> {
        return universitiesLiveData
    }

    override suspend fun getUniversityByName(universityName: String): UniversityDto? {
        return universities.firstOrNull { it.name == universityName }
    }

    override suspend fun getProvinces(pageNumber: Int): Response {
        return Response(1, 1, 1, 1, 1, fakeProvinces)
    }

    private fun refreshData() {
        universitiesLiveData.postValue(universities)
    }

    fun setFakeProvinces(provinces: List<ProvinceDto>) {
        fakeProvinces.clear()
        fakeProvinces.addAll(provinces)
    }

    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

}