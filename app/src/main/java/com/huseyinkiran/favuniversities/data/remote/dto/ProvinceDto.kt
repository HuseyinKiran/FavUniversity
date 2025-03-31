package com.huseyinkiran.favuniversities.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProvinceDto(
    val id: Int,
    @SerializedName("province")
    val name: String,
    val universities: List<UniversityDto>
)