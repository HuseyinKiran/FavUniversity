package com.huseyinkiran.favuniversities.presentation.mapper

import com.huseyinkiran.favuniversities.domain.model.City
import com.huseyinkiran.favuniversities.domain.model.University
import com.huseyinkiran.favuniversities.presentation.model.CityUIModel
import com.huseyinkiran.favuniversities.presentation.model.UniversityUIModel

fun City.toUI(): CityUIModel {
    val universityUI = universities.map { it.toUI() }
    return CityUIModel(
        id = 0,
        name = name,
        universities = universityUI,
        isExpanded = false
    )
}

fun University.toUI(): UniversityUIModel {
    val expandable = listOf(address, fax, phone, rector, website)
        .any { it.isNotBlank() && it != "-" }
    return UniversityUIModel(
        name, address, fax, phone, rector, website,
        isFavorite = isFavorite,
        isExpandable = expandable,
        isExpanded = false
    )
}

fun UniversityUIModel.toDomain(): University = University(
    name = name,
    address = address,
    fax = fax,
    phone = phone,
    rector = rector,
    website = website,
    isFavorite = isFavorite
)