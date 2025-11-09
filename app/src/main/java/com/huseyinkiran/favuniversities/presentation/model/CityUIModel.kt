package com.huseyinkiran.favuniversities.presentation.model

data class CityUIModel(
    val id: Int,
    val name: String,
    val universities: List<UniversityUIModel>,
    var isExpanded: Boolean
)