package com.huseyinkiran.favuniversities.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.favuniversities.model.Province
import com.huseyinkiran.favuniversities.repository.UniversityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ProvinceViewModel @Inject constructor(
    private val universityRepository: UniversityRepository
) : ViewModel() {

    private val _provinceList = MutableLiveData<List<Province>>()
    val provinceList: LiveData<List<Province>> = _provinceList

    private val _expandedProvinces = MutableLiveData<MutableSet<String>>()
    val expandedProvinces : LiveData<MutableSet<String>> = _expandedProvinces

    private val _errorMessage = MutableLiveData<Boolean>()
    val errorMessage: LiveData<Boolean> get() = _errorMessage

    private val _expandedUniversities = MutableLiveData<MutableSet<String>>()
    val expandedUniversities: LiveData<MutableSet<String>> = _expandedUniversities

    private var currentList: MutableList<Province> = mutableListOf()
    private var currentPage = 1
    private var isLoading = false


    fun checkProvinceExpansion(provinceName: String) {
        val currentSet = _expandedProvinces.value ?: mutableSetOf()
        if (currentSet.contains(provinceName)) {
            currentSet.remove(provinceName)
        } else {
            currentSet.add(provinceName)
        }
        _expandedProvinces.value = currentSet
    }

    fun checkUniversityExpansion(universityName: String) {
        val currentSet = _expandedUniversities.value ?: mutableSetOf()
        if (currentSet.contains(universityName)) {
            currentSet.remove(universityName)
        } else {
            currentSet.add(universityName)
        }
        _expandedUniversities.value = currentSet
    }

    fun loadProvinces() {
        if (isLoading || currentPage > 3) {
            return
        }

        isLoading = true

        viewModelScope.launch {
            try {
                val response = universityRepository.getProvinces(currentPage)

                if (response.data.isNotEmpty()) {
                    currentList.addAll(response.data)
                    _provinceList.value = currentList
                    currentPage++
                    _errorMessage.value = false
                } else {
                    _errorMessage.value = true
                }
            } catch (e: HttpException) {
                _errorMessage.value = true
            } catch (e: Exception) {
                _errorMessage.value = true
            } finally {
                isLoading = false
            }
        }
    }

}