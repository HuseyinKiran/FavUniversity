package com.huseyinkiran.favuniversities.data.mapper

import com.huseyinkiran.favuniversities.data.local.UniversityEntity
import com.huseyinkiran.favuniversities.data.remote.dto.CityDto
import com.huseyinkiran.favuniversities.data.remote.dto.UniversityDto
import com.huseyinkiran.favuniversities.domain.model.City
import com.huseyinkiran.favuniversities.domain.model.University


fun CityDto.toCity(): City {
    return City(
        name = name,
        universities = universities.map { it.toUniversity() },
    )
}

fun UniversityEntity.toUniversity(): University {
    return University(
        name = name,
        address = address,
        fax = fax,
        phone = phone,
        rector = rector,
        website = website,
        isFavorite = true
    )
}

fun UniversityDto.toUniversity(): University {
    return University(
        name = name,
        address = address,
        fax = fax,
        phone = phone,
        rector = rector,
        website = website,
        isFavorite = false
    )
}

fun University.toEntity(): UniversityEntity {
    return UniversityEntity(
        name = name,
        fax = fax,
        phone = phone,
        website = website,
        address = address,
        rector = rector,
    )
}