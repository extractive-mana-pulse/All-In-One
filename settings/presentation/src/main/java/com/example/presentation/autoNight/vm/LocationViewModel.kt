package com.example.presentation.autoNight.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.LocationResult
import com.example.domain.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _locationState = MutableStateFlow<LocationResult>(LocationResult.Loading)
    val locationState: StateFlow<LocationResult> = _locationState.asStateFlow()

    init {
        fetchLocation()
    }

    fun fetchLocation() {
        viewModelScope.launch {
            locationRepository.fetchCurrentLocationAddress()
                .collect { result ->
                    _locationState.value = result
                }
        }
    }

    fun refreshLocation() {
        fetchLocation()
    }
}