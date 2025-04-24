package com.huseyinkiran.favuniversities.domain.model

import com.huseyinkiran.favuniversities.data.remote.dto.CityDto
import com.huseyinkiran.favuniversities.data.remote.dto.UniversityDto

data class CityUIModel(
    val name: String,
    val universities: List<UniversityUIModel>,
    val isExpandable: Boolean,
    var isExpanded: Boolean
)

fun CityDto.toUIModel(): CityUIModel {
    return CityUIModel(
        name = name,
        universities = universities.map { it.toUIModel() },
        isExpandable = isCityExpandable(universities = this.universities),
        isExpanded = false,
    )
}

fun isCityExpandable(universities: List<UniversityDto>): Boolean {
    return universities.isNotEmpty()
}