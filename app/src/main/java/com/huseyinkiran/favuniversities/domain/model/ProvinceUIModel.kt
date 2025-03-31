package com.huseyinkiran.favuniversities.domain.model

import com.huseyinkiran.favuniversities.data.remote.dto.ProvinceDto
import com.huseyinkiran.favuniversities.data.remote.dto.UniversityDto

data class ProvinceUIModel(
    val name: String,
    val universities: List<UniversityUIModel>,
    val isExpandable: Boolean,
    var isExpanded: Boolean
)

fun ProvinceDto.toUI(): ProvinceUIModel {
    return ProvinceUIModel(
        name = name,
        universities = universities.map { it.toUI() },
        isExpandable = isProvinceExpandable(universities = this.universities),
        isExpanded = false,
    )
}

fun isProvinceExpandable(universities: List<UniversityDto>): Boolean {
    return universities.isNotEmpty()
}