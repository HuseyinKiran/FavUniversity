package com.huseyinkiran.favuniversities.model

import com.huseyinkiran.favuniversities.model.dto.UniversityDto


data class University(
    val name: String,
    val address: String,
    val fax: String,
    val phone: String,
    val rector: String,
    val website: String,
    val isExpandable: Boolean,
    var isExpanded: Boolean,
    var isFavorite: Boolean
)

fun UniversityDto.toUI() : University {
    return University(
        name = name,
        address = address,
        fax = fax,
        phone = phone,
        rector = rector,
        website = website,
        isExpandable = isUniversityExpandable(university = this),
        isExpanded = false,
        isFavorite = false
    )
}

fun University.toUniversity(): UniversityDto {
    return UniversityDto(
        id = null,
        name = name,
        address = address,
        email = "",
        fax = fax,
        phone = phone,
        rector = rector,
        website = website
    )
}


fun isUniversityExpandable(university: UniversityDto): Boolean {
    return !(university.rector == "-" && university.phone == "-" && university.fax == "-"
            && university.address == "-" && university.email == "-")
}