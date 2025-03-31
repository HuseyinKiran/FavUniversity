package com.huseyinkiran.favuniversities.domain.model

import com.huseyinkiran.favuniversities.data.local.UniversityEntity
import com.huseyinkiran.favuniversities.data.remote.dto.UniversityDto

data class UniversityUIModel(
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

fun UniversityEntity.toUI(): UniversityUIModel {
    return UniversityUIModel(
        name = name,
        address = address,
        fax = fax,
        phone = phone,
        rector = rector,
        website = website,
        isExpandable = isUniversityExpandable(university = this.toDto()),
        isExpanded = false,
        isFavorite = true
    )
}

fun UniversityDto.toUI(): UniversityUIModel {
    return UniversityUIModel(
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

fun UniversityEntity.toDto(): UniversityDto {
    return UniversityDto(
        name = name,
        address = address,
        email = "",
        fax = fax,
        phone = phone,
        rector = rector,
        website = website,
    )
}

fun UniversityUIModel.toEntity(): UniversityEntity {
    return UniversityEntity(
        name = name,
        fax = fax,
        phone = phone,
        website = website,
        address = address,
        rector = rector,
    )
}


fun isUniversityExpandable(university: UniversityDto): Boolean {
    return !(university.rector == "-" && university.phone == "-" && university.fax == "-"
            && university.address == "-" && university.website == "-")
}