package com.huseyinkiran.favuniversities.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CityDto(
    val id: Int,
    @SerializedName("province")
    val name: String,
    val universities: List<UniversityDto>
)