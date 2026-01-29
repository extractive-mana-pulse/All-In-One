package com.example.domain

interface OnBoardingPreferencesRepo {

    fun isOnBoardingCompleted(): Boolean

    fun setOnBoardingCompleted(completed: Boolean)

    fun resetOnBoarding()

}