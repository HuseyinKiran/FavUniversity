package com.huseyinkiran.favuniversities.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.favuniversities.model.University
import com.huseyinkiran.favuniversities.repository.UniversityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UniversityViewModel @Inject constructor(
    private val repository: UniversityRepository
) : ViewModel() {

    val favoriteUniversities: LiveData<List<University>> = repository.getAllUniversities()

    private val _expandedUniversities = MutableLiveData<MutableList<String>>()
    val expandedUniversities: LiveData<MutableList<String>> = _expandedUniversities

    fun checkUniversityExpansion(universityName: String) {
        val currentSet = _expandedUniversities.value ?: mutableListOf()
        if (currentSet.contains(universityName)) {
            currentSet.remove(universityName)
        } else {
            currentSet.add(universityName)
        }
        _expandedUniversities.value = currentSet
    }

    fun isUniversityExpandable(university: University): Boolean {
        return !(university.rector == "-" && university.phone == "-" && university.fax == "-"
                && university.address == "-" && university.email == "-")
    }

    fun toggleFavorite(university: University) = viewModelScope.launch {
        val universityInDb = repository.getUniversityByName(university.name)
        if (universityInDb == null) {
            upsertUniversity(university)
        } else {
            deleteUniversity(universityInDb)
        }
    }

    fun upsertUniversity(university: University) = viewModelScope.launch {
        repository.upsertUniversity(university)
    }

    fun deleteUniversity(university: University) = viewModelScope.launch {
        repository.deleteUniversity(university)
    }

}