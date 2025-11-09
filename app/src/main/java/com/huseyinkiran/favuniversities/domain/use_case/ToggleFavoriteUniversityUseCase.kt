package com.huseyinkiran.favuniversities.domain.use_case

import com.huseyinkiran.favuniversities.domain.model.University
import com.huseyinkiran.favuniversities.domain.repository.UniversityRepository
import javax.inject.Inject

class ToggleFavoriteUniversityUseCase @Inject constructor(
    private val repository: UniversityRepository
) {

    suspend operator fun invoke(university: University) = runCatching {
        val existing = repository.getUniversityByName(university.name)
        if (existing == null) {
            repository.upsertUniversity(university)
        } else {
            repository.deleteUniversity(university)
        }
    }

}