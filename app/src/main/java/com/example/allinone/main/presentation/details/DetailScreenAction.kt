package com.example.allinone.main.presentation.details

sealed interface DetailScreenAction {
    object OnSnackbarDismiss: DetailScreenAction
    data class OnSnackbarActionPerformed(val enabled : Boolean): DetailScreenAction
    data class OnCourseLoaded(val courseId: Int): DetailScreenAction
    object OnSnackbarShown: DetailScreenAction
    object OnNavigateAway: DetailScreenAction
}