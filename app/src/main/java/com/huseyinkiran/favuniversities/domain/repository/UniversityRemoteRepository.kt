package com.huseyinkiran.favuniversities.domain.repository

import com.huseyinkiran.favuniversities.domain.model.ProvinceUIModel

interface UniversityRemoteRepository {

    suspend fun getUniversities(pageNumber: Int) : List<ProvinceUIModel>

}