package com.huseyinkiran.favuniversities.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.huseyinkiran.favuniversities.data.remote.dto.ProvinceDto
import com.huseyinkiran.favuniversities.data.remote.dto.Response
import com.huseyinkiran.favuniversities.data.local.UniversityEntity
import com.huseyinkiran.favuniversities.domain.repository.UniversityRepository

class FakeUniversityRepository : UniversityRepository {

    private val universities = mutableListOf<UniversityEntity>()
    private val universitiesLiveData = MutableLiveData<List<UniversityEntity>>(universities)

    private val fakeProvinces = mutableListOf<ProvinceDto>()
    private var shouldReturnError = false

    override suspend fun upsertUniversity(university: UniversityEntity) {
        universities.add(university)
        refreshData()
    }

    override suspend fun deleteUniversity(university: UniversityEntity) {
        universities.remove(university)
        refreshData()
    }

    override fun getAllUniversities(): LiveData<List<UniversityEntity>> {
        return universitiesLiveData
    }

    override suspend fun getUniversityByName(universityName: String): UniversityEntity? {
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