package com.huseyinkiran.favuniversities.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.favuniversities.domain.model.ProvinceUIModel
import com.huseyinkiran.favuniversities.domain.model.UniversityUIModel
import com.huseyinkiran.favuniversities.domain.repository.UniversityRepository
import com.huseyinkiran.favuniversities.utils.PermissionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UniversityRepository,
    private val permission: PermissionRepository
) : ViewModel() {

    private val _provinceList = MutableLiveData<List<ProvinceUIModel>>()
    val provinceList: LiveData<List<ProvinceUIModel>> = _provinceList

    val favorites: StateFlow<List<UniversityUIModel>> = repository.getAllFavorites().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _callPhoneEvent = MutableLiveData<String?>()
    val callPhoneEvent: LiveData<String?> = _callPhoneEvent

    private val _errorMessage = MutableLiveData<Boolean>()
    val errorMessage: LiveData<Boolean> get() = _errorMessage

    private var currentList: MutableList<ProvinceUIModel> = mutableListOf()
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

    fun upsertUniversity(university: UniversityUIModel) = viewModelScope.launch {
        repository.upsertUniversity(university)
    }

    fun deleteUniversity(university: UniversityUIModel) = viewModelScope.launch {
        repository.deleteUniversity(university)
    }

    fun toggleFavorite(university: UniversityUIModel) = viewModelScope.launch {
        val universityInDb = repository.getUniversityByName(university.name)
        if (universityInDb == null) {
            upsertUniversity(university)
        } else {
            deleteUniversity(university)
        }
    }

    fun loadProvinces() {

        if (isLoading || currentPage > 3) return
        isLoading = true

        viewModelScope.launch {
            try {
                val response = repository.getUniversities(currentPage)
                if (response.isNotEmpty()) {
                    currentList.addAll(response)
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