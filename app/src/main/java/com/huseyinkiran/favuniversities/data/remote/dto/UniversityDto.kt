package com.huseyinkiran.favuniversities.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UniversityDto(
    val name: String,
    @SerializedName("adress")
    val address: String,
    val email: String,
    val fax: String,
    val phone: String,
    val rector: String,
    val website: String,
)