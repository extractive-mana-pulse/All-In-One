package com.example.allinone.navigation.navs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.allinone.navigation.screens.BlogsScreens
import com.example.presentation.BlogsScreen

internal fun NavGraphBuilder.blogsNavigation(navController: NavHostController) {
    composable<BlogsScreens.Blogs> {
        BlogsScreen(
            navigateToRichTextEditor = {
                navController.navigate(BlogsScreens.Blogs.RichTextEditor)
            },
            onNavigateUp = {
                navController.navigateUp()
            }
        )
    }
    composable<BlogsScreens.Blogs.RichTextEditor> {
        _root_ide_package_.com.example.presentation.rich_text_editor.RichTextEditorRoot(
            onNavigateUp = {
                navController.navigateUp()
            }
        )
    }
}