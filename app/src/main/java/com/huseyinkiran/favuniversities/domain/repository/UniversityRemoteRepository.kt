package com.huseyinkiran.favuniversities.domain.repository

import com.huseyinkiran.favuniversities.domain.model.CityUIModel

interface UniversityRemoteRepository {

    suspend fun getUniversities(pageNumber: Int) : List<CityUIModel>

    suspend fun getTotalPage(): Int

    suspend fun getPageSize(): Int
}