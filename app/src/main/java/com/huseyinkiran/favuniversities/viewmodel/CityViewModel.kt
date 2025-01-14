package com.huseyinkiran.favuniversities.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.favuniversities.model.Province
import com.huseyinkiran.favuniversities.model.University
import com.huseyinkiran.favuniversities.repository.UniversityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val universityRepository: UniversityRepository
) : ViewModel() {

    private val _provinceList = MutableLiveData<List<Province>>()
    val provinceList: LiveData<List<Province>> = _provinceList

    private val _errorMessage = MutableLiveData<Boolean>()
    val errorMessage: LiveData<Boolean> get() = _errorMessage

    private var currentList: MutableList<Province> = mutableListOf()
    private var currentPage = 1
    private var isLoading = false // Yeni veriler yükleniyor mu?

    fun loadProvinces() {
        if (isLoading || currentPage > 3) { // Maksimum 3 sayfa yüklenir
            Log.d("CityViewModel", "No more pages to load. Current page: $currentPage")
            return
        }

        isLoading = true

        viewModelScope.launch {
            try {
                val response = universityRepository.getProvinces(currentPage)

                if (response.data.isNotEmpty()) {
                    currentList.addAll(response.data)
                    _provinceList.value = currentList
                    currentPage++ // Yalnızca başarılı bir yüklemeden sonra artırılır
                    _errorMessage.value = false
                } else {
                    Log.e("CityViewModel", "Empty data received")
                    _errorMessage.value = true
                }
            } catch (e: HttpException) {
                Log.e("CityViewModel", "HTTP Exception: ${e.message}")
                _errorMessage.value = true
            } catch (e: Exception) {
                Log.e("CityViewModel", "Unexpected error: ${e.message}")
                _errorMessage.value = true
            } finally {
                isLoading = false
            }
        }
    }


}


