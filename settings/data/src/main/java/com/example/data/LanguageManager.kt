package com.example.data

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.domain.repository.LanguageManagerRepo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LanguageManager @Inject constructor(
    @ApplicationContext private val context: Context
) : LanguageManagerRepo {

    override fun changeLanguage(languageCode: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(languageCode)
        } else {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(languageCode)
            )
        }
    }

    override fun getLanguageCode(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java)
                .applicationLocales[0]
                ?.toLanguageTag()
                ?.split("-")
                ?.first()
                ?: "en"
        } else {
            AppCompatDelegate.getApplicationLocales()[0]
                ?.toLanguageTag()
                ?.split("-")
                ?.first()
                ?: "en"
        }
    }
}