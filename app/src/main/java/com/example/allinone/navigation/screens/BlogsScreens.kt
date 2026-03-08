package com.example.allinone.navigation.screens

import kotlinx.serialization.Serializable

@Serializable
sealed interface BlogsScreens {

    @Serializable
    data object Blogs : BlogsScreens {
        @Serializable
        data object RichTextEditor : BlogsScreens {
        }
    }
}
