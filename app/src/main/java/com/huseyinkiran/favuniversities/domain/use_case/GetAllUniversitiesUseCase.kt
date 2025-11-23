package com.huseyinkiran.favuniversities.domain.use_case

import com.huseyinkiran.favuniversities.domain.model.University
import com.huseyinkiran.favuniversities.domain.repository.UniversityRepository
import javax.inject.Inject

class GetAllUniversitiesUseCase @Inject constructor(
    private val repository: UniversityRepository
) {

    suspend operator fun invoke(): List<University> {
        val universities = mutableListOf<University>()

        val totalPage = 3

        for (page in 1..totalPage) {
            val cities = repository.getCities(page)
            for (city in cities) {
                universities += city.universities
            }
        }

        return universities
    }
}