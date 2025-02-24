package com.huseyinkiran.favuniversities.domain.model

import com.huseyinkiran.favuniversities.data.local.UniversityEntity

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

fun UniversityEntity.toUI() : University {
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

fun University.toUniversity(): UniversityEntity {
    return UniversityEntity(
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


fun isUniversityExpandable(university: UniversityEntity): Boolean {
    return !(university.rector == "-" && university.phone == "-" && university.fax == "-"
            && university.address == "-" && university.website == "-")
}