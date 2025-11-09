package com.huseyinkiran.favuniversities.domain.model

data class University(
    val name: String,
    val address: String,
    val fax: String,
    val phone: String,
    val rector: String,
    val website: String,
    var isFavorite: Boolean
)