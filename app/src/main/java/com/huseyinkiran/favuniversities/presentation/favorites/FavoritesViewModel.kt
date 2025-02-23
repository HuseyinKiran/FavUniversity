package com.huseyinkiran.favuniversities.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.favuniversities.data.local.UniversityEntity
import com.huseyinkiran.favuniversities.domain.repository.UniversityRepository
import com.huseyinkiran.favuniversities.utils.PermissionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: UniversityRepository,
    private val permission: PermissionRepository
) : ViewModel() {

    val favoriteUniversities: LiveData<List<UniversityEntity>> = repository.getAllUniversities()

    private val _callPhoneEvent = MutableLiveData<String?>()
    val callPhoneEvent: LiveData<String?> = _callPhoneEvent

    fun increaseDeniedCount() {
        permission.increaseDeniedCount()
    }

    fun shouldRedirectToSettings() : Boolean {
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

}