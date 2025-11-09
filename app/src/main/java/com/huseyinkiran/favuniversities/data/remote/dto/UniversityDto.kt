package com.huseyinkiran.favuniversities.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UniversityDto(
    val id: Int,
    @SerializedName("university_type") val universityType: String,
    val name: String,
    val phone: String,
    val fax: String,
    val website: String,
    val email: String,
    val address: String,
    val rector: String,
)