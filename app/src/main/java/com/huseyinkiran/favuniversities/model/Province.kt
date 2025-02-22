package com.huseyinkiran.favuniversities.model

import com.huseyinkiran.favuniversities.model.dto.ProvinceDto
import com.huseyinkiran.favuniversities.model.dto.UniversityDto

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

fun isProvinceExpandable(universities: List<UniversityDto>): Boolean {
    return universities.isNotEmpty()
}