package com.reactivemobile.rainfall.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.reactivemobile.rainfall.domain.repository.RainfallRepository

class RainfallViewModelFactory(private val repository: RainfallRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RainfallViewModel(repository) as T
    }
}