package com.huseyinkiran.favuniversities.model.dto

data class Response(
    val currentPage: Int,
    val itemPerPage: Int,
    val pageSize: Int,
    val total: Int,
    val totalPage: Int,
    val data: List<ProvinceDto>
)