package com.huseyinkiran.favuniversities.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.huseyinkiran.favuniversities.model.Province
import com.huseyinkiran.favuniversities.model.Response
import com.huseyinkiran.favuniversities.model.University

class FakeUniversityRepository : UniversityRepository {

    private val universities = mutableListOf<University>()
    private val universitiesLiveData = MutableLiveData<List<University>>(universities)

    private val fakeProvinces = mutableListOf<Province>()
    private var shouldReturnError = false

    override suspend fun upsertUniversity(university: University) {
        universities.add(university)
        refreshData()
    }

    override suspend fun deleteUniversity(university: University) {
        universities.remove(university)
        refreshData()
    }

    override fun getAllUniversities(): LiveData<List<University>> {
        return universitiesLiveData
    }

    override suspend fun getUniversityByName(universityName: String): University? {
        return universities.firstOrNull { it.name == universityName }
    }

    override suspend fun getProvinces(pageNumber: Int): Response {
        return Response(1, 1, 1, 1, 1, fakeProvinces)
    }

    private fun refreshData() {
        universitiesLiveData.postValue(universities)
    }

    fun setFakeProvinces(provinces: List<Province>) {
        fakeProvinces.clear()
        fakeProvinces.addAll(provinces)
    }

    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

}