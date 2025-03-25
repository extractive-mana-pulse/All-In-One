package com.example.allinone.settings.presentation.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinone.settings.domain.model.Twilight
import com.example.allinone.settings.domain.repository.TwilightRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val repository: TwilightRepository
) : ViewModel() {

    private val _twilight = MutableStateFlow(Twilight())
    val twilight = _twilight.asStateFlow()

    fun getTwilight(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val response = repository.getTwilight(latitude, longitude)
                _twilight.value = response
            } catch (e: Exception) {
                Log.e("ThemeViewModel", "Error fetching twilight data", e)
            }
        }
    }
}
