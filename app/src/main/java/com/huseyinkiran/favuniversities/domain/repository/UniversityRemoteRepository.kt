package com.huseyinkiran.favuniversities.domain.repository

import com.huseyinkiran.favuniversities.domain.model.City

interface UniversityRemoteRepository {

    suspend fun getCities(pageNumber: Int) : List<City>

}