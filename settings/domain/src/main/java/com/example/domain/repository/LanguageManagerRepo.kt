package com.example.domain.repository

interface LanguageManagerRepo {
    fun changeLanguage(languageCode: String)
    fun getLanguageCode(): String
}