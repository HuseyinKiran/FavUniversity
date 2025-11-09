package com.huseyinkiran.favuniversities.domain.use_case

import com.huseyinkiran.favuniversities.domain.repository.UniversityRepository
import javax.inject.Inject

class GetAllFavoritesUseCase @Inject constructor(
    private val repository: UniversityRepository
) {

    operator fun invoke() = repository.getAllFavorites()

}