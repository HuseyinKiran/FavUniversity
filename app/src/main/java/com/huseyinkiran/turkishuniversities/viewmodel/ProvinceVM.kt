package com.huseyinkiran.turkishuniversities.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.turkishuniversities.model.Province
import com.huseyinkiran.turkishuniversities.model.University
import com.huseyinkiran.turkishuniversities.repository.UniversityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ProvinceVM(
    application: Application, private val repository: UniversityRepository
) : AndroidViewModel(application) {

    private val _provinceList = MutableLiveData<List<Province>>()
    val provinceList: LiveData<List<Province>> = _provinceList

    private val _errorMessage = MutableLiveData<Boolean>()
    val errorMessage: LiveData<Boolean> get() = _errorMessage

    fun loadProvinces() {
        viewModelScope.launch {
            try {
                val response1 = repository.getProvinces(1)
                _provinceList.value = response1.data
                val response2 = repository.getProvinces(2)
                _provinceList.value = response2.data
                val response3 = repository.getProvinces(3)
                _provinceList.value = response3.data
                _errorMessage.value = false
            } catch (e: Exception) {
                _provinceList.value = emptyList()
                _errorMessage.value = true
            }
        }
    }


    fun updateFavorites(university: University) {
        viewModelScope.launch(Dispatchers.IO) {
            val isCurrentlyFavorite = repository.isUniversityFavorite(university.name)
            if (isCurrentlyFavorite) {
                repository.deleteUniversity(university)
            } else {
                repository.upsertUniversity(university)
            }
        }
    }

    fun getFavoriteUniversities() = repository.getFavoriteUniversities()

    fun isUniversityFavorite(universityName: String?): LiveData<Boolean> {
        val isFavorite = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.isUniversityFavorite(universityName)
            isFavorite.postValue(result)
        }
        return isFavorite
    }

}

