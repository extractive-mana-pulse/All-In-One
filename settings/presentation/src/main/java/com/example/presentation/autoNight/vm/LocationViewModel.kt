package com.example.presentation.autoNight.vm

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository
): ViewModel() {

    private val _location = mutableStateOf("Fetching location...")
    val location: State<String> = _location

    fun getLocation(
        context: Context,
        fusedLocationClient: FusedLocationProviderClient
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            locationRepository.fetchLocationAndAddress(
                context = context,
                fusedLocationClient = fusedLocationClient,
            ) { address ->
                _location.value = address ?: "Unable to fetch address"
            }
        }
    }

    private val _refreshedState = MutableStateFlow<LocationRefreshState>(LocationRefreshState.Loading)
    val refreshedState = _refreshedState.asStateFlow()

    fun refreshLocation(
        context: Context,
        fusedLocationClient: FusedLocationProviderClient
    ) {
        _refreshedState.value = LocationRefreshState.Loading
        _location.value = "Updating location..."
        getLocation(context, fusedLocationClient)
    }
}

sealed class LocationRefreshState {

    object Loading: LocationRefreshState()

    object Loaded: LocationRefreshState()

    data class Error(val message: String): LocationRefreshState()
}