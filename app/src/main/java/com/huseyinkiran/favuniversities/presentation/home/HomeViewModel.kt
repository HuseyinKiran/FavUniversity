package com.huseyinkiran.favuniversities.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.favuniversities.domain.model.CityUIModel
import com.huseyinkiran.favuniversities.domain.model.UniversityUIModel
import com.huseyinkiran.favuniversities.domain.repository.UniversityRepository
import com.huseyinkiran.favuniversities.utils.PermissionRepository
import com.huseyinkiran.favuniversities.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UniversityRepository,
    private val permission: PermissionRepository
) : ViewModel() {

    private val _cityList = MutableStateFlow<Resource<List<CityUIModel>>>(Resource.Loading())
    val cityList: Flow<Resource<List<CityUIModel>>> = _cityList

    val favorites: StateFlow<List<UniversityUIModel>> = repository.getAllFavorites().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _callPhoneEvent = MutableLiveData<String?>()
    val callPhoneEvent: LiveData<String?> = _callPhoneEvent

    private var currentList: MutableList<CityUIModel> = mutableListOf()
    private var currentPage = 1
    private val mutex = Mutex()

    init {
        loadCities()
    }

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

    private var isLoading = false
    fun loadCities()  {
        if (isLoading || currentPage > 3) return

        viewModelScope.launch {
            mutex.withLock {
                isLoading = true
                try {
                    _cityList.value = Resource.Loading()
                    val response = repository.getUniversities(pageNumber = currentPage)
                    if (response.isNotEmpty()) {
                        currentList.addAll(response)
                        _cityList.value = Resource.Success(currentList.toList())
                        currentPage++
                    }
                } catch (e: Exception) {
                    _cityList.value = Resource.Error(e.localizedMessage ?: "Bilinmeyen Hata !")
                } finally {
                    isLoading = false
                }
            }
        }
    }

}