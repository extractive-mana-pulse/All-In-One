package com.example.presentation.autoNight.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.LanguageManagerRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val languageManager: LanguageManagerRepo
) : ViewModel() {

    private val _currentLanguage = MutableStateFlow("en")
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    private val _selectedLanguage = MutableStateFlow("English")
    val selectedLanguage: StateFlow<String> = _selectedLanguage.asStateFlow()

    val languageMapping = mapOf(
        "English" to "en",
        "Russian" to "ru",
        "Uzbek" to "uz"
    )

    init {
        loadCurrentLanguage()
    }

    private fun loadCurrentLanguage() {
        viewModelScope.launch {
            val currentCode = languageManager.getLanguageCode()
            _currentLanguage.value = currentCode
            _selectedLanguage.value = languageMapping.entries
                .find { it.value == currentCode }?.key ?: "English"
        }
    }

    fun changeLanguage(language: String) {
        viewModelScope.launch {
            _selectedLanguage.value = language
            val languageCode = languageMapping[language] ?: "en"
            languageManager.changeLanguage(languageCode)
            _currentLanguage.value = languageCode
        }
    }
}