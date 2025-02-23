package com.huseyinkiran.favuniversities.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.huseyinkiran.favuniversities.data.local.UniversityEntity

data class ProvinceDto(
    val id: Int,
    @SerializedName("province")
    val name: String,
    val universities: List<UniversityEntity>
)