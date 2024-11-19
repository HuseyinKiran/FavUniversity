package com.huseyinkiran.turkishuniversities.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.huseyinkiran.turkishuniversities.repository.UniversityRepository

class ProvinceVMFactory(
    private val app: Application,
    private val repository: UniversityRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProvinceVM(app, repository) as T
    }

}