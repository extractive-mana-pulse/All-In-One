package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.domain.OnBoardingPreferencesRepo

class OnBoardingPreferences(context: Context): OnBoardingPreferencesRepo {
    private val prefs: SharedPreferences = 
        context.getSharedPreferences("onboarding_prefs", Context.MODE_PRIVATE)

    
    companion object {
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
    }
    
    override fun isOnBoardingCompleted(): Boolean {
        return prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)
    }
    
    override fun setOnBoardingCompleted(completed: Boolean) {
        prefs.edit().putBoolean(KEY_ONBOARDING_COMPLETED, completed).apply()
    }
    
    override fun resetOnBoarding() {
        prefs.edit().putBoolean(KEY_ONBOARDING_COMPLETED, false).apply()
    }
}