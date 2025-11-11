package com.huseyinkiran.favuniversities.presentation.model

data class UniversityUIModel(
    val id: Int,
    val universityType: String,
    val name: String,
    val address: String,
    val fax: String,
    val phone: String,
    val rector: String,
    val website: String,
    val email: String,
    val isFavorite: Boolean,
    val isExpandable: Boolean,
    var isExpanded: Boolean
)