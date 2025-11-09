package com.huseyinkiran.favuniversities.data.remote.dto

data class Response(
    val currentPage: Int,
    val totalPage: Int,
    val total: Int,
    val pageSize: Int,
    val itemPerPage: Int,
    val data: List<CityDto>
)