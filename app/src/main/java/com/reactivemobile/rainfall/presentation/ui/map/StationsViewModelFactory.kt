package com.reactivemobile.rainfall.presentation.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.reactivemobile.rainfall.domain.repository.RainfallRepository

class StationsViewModelFactory(private val repository: RainfallRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StationsViewModel(repository) as T
    }
}