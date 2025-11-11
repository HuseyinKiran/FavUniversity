package com.huseyinkiran.favuniversities.domain.model

data class University(
    val id: Int,
    val universityType: String,
    val name: String,
    val address: String,
    val fax: String,
    val phone: String,
    val rector: String,
    val website: String,
    val email: String,
    var isFavorite: Boolean
)