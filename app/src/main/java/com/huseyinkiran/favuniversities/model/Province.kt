package com.huseyinkiran.favuniversities.model

data class Province(
    val id: Int,
    val province: String,
    val universities: List<University>
)

