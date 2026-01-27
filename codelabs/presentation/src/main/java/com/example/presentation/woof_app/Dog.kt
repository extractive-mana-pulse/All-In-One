package com.example.presentation.woof_app

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Dog(
    @DrawableRes val imageResourceId: Int,
    @StringRes val name: Int,
    val age: Int,
    @StringRes val hobbies: Int
)