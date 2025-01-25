package com.huseyinkiran.favuniversities.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.favuniversities.model.University
import com.huseyinkiran.favuniversities.repository.UniversityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UniversityViewModel @Inject constructor(
    private val repository: UniversityRepository
) : ViewModel() {

    val favoriteUniversities: LiveData<List<University>> = repository.getAllUniversities()

    private val _expandedUniversities = MutableLiveData<MutableSet<String>>()
    val expandedUniversities: LiveData<MutableSet<String>> = _expandedUniversities


    fun checkUniversityExpansion(universityName: String) {
        val currentSet = _expandedUniversities.value ?: mutableSetOf()
        if (currentSet.contains(universityName)) {
            currentSet.remove(universityName)
        } else {
            currentSet.add(universityName)
        }
        _expandedUniversities.value = currentSet
    }

    fun updateFavorites(university: University) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val universityInDb = repository.getUniversityByName(university.name)
                if (universityInDb != null) {
                    repository.deleteUniversity(universityInDb)
                    university.isFavorite = false
                } else {
                    repository.upsertUniversity(university)
                    university.isFavorite = true
                }

                university.isFavorite = universityInDb == null

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

}