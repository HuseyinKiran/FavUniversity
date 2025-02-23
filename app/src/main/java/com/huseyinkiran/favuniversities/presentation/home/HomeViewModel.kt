package com.huseyinkiran.favuniversities.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.favuniversities.data.remote.dto.ProvinceDto
import com.huseyinkiran.favuniversities.data.local.UniversityEntity
import com.huseyinkiran.favuniversities.domain.repository.UniversityRepository
import com.huseyinkiran.favuniversities.utils.PermissionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UniversityRepository,
    private val permission: PermissionRepository
) : ViewModel() {

    val favoriteUniversities: LiveData<List<UniversityEntity>> = repository.getAllUniversities()

    private val _provinceList = MutableLiveData<List<ProvinceDto>>()
    val provinceList: LiveData<List<ProvinceDto>> = _provinceList

    private val _callPhoneEvent = MutableLiveData<String?>()
    val callPhoneEvent: LiveData<String?> = _callPhoneEvent

    private val _errorMessage = MutableLiveData<Boolean>()
    val errorMessage: LiveData<Boolean> get() = _errorMessage

    private var currentList: MutableList<ProvinceDto> = mutableListOf()
    private var currentPage = 1
    private var isLoading = false

    fun increaseDeniedCount() {
        permission.increaseDeniedCount()
    }

    fun shouldRedirectToSettings(): Boolean {
        return permission.shouldRedirectToSettings()
    }

    fun requestCall(phoneNumber: String) {
        _callPhoneEvent.value = phoneNumber
    }

    fun clearCallEvent() {
        _callPhoneEvent.value = null
    }

    fun upsertUniversity(university: UniversityEntity) = viewModelScope.launch {
        repository.upsertUniversity(university)
    }

    fun deleteUniversity(university: UniversityEntity) = viewModelScope.launch {
        repository.deleteUniversity(university)
    }

    fun toggleFavorite(university: UniversityEntity) = viewModelScope.launch {
        val universityInDb = repository.getUniversityByName(university.name)
        if (universityInDb == null) {
            upsertUniversity(university)
        } else {
            deleteUniversity(universityInDb)
        }
    }

    fun loadProvinces() {

        if (isLoading || currentPage > 3) return
        isLoading = true

        viewModelScope.launch {
            try {
                val response = repository.getProvinces(currentPage)
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