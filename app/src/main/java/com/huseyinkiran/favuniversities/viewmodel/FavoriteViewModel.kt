package com.huseyinkiran.favuniversities.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.favuniversities.model.University
import com.huseyinkiran.favuniversities.repository.UniversityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: UniversityRepository
) : ViewModel() {

    val favoriteUniversities: LiveData<List<University>> = repository.getAllUniversities()

    fun updateFavorites(university: University) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val universityInDb = repository.getUniversityByName(university.name)

                Log.d(
                    "FAVORITE_CHECK",
                    "University ${university.name} isFavorite: ${universityInDb?.isFavorite}"
                )

                if (universityInDb != null) {
                    repository.deleteUniversity(universityInDb)
                    Log.d("FAVORITE_ACTION", "Deleted ${university.name} from favorites")
                    university.isFavorite = false
                } else {
                    repository.upsertUniversity(university)
                    Log.d("FAVORITE_ACTION", "Added ${university.name} to favorites")
                    university.isFavorite = true
                }

                university.isFavorite = universityInDb == null

            } catch (e: Exception) {
                Log.e("DATABASE_ERROR", "Error updating favorites: ${e.message}")
                e.printStackTrace()
            }

        }
    }

}