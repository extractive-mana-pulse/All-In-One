package com.example.presentation.rich_text_editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.RteRepositoryRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class RteViewModel(
    private val rteRepository: RteRepositoryRepo
) : ViewModel() {

    val isRteEnabled = rteRepository.isRteEnabled
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    fun toggleRteMode(enabled: Boolean) {
        viewModelScope.launch {
            rteRepository.setRteEnabled(enabled)
        }
    }
}