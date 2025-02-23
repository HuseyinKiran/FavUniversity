package com.huseyinkiran.favuniversities.domain.model

import com.huseyinkiran.favuniversities.data.remote.dto.ProvinceDto
import com.huseyinkiran.favuniversities.data.local.UniversityEntity

data class Province(
    val name: String,
    val universities: List<University>,
    val isExpandable: Boolean,
    var isExpanded: Boolean
)

fun ProvinceDto.toUI(): Province {
    return Province(
        name = name,
        universities = universities.map { it.toUI() },
        isExpandable = isProvinceExpandable(universities = this.universities),
        isExpanded = false,
    )
}

fun isProvinceExpandable(universities: List<UniversityEntity>): Boolean {
    return universities.isNotEmpty()
}