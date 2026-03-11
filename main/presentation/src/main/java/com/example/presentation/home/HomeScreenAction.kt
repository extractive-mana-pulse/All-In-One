package com.example.presentation.home

sealed interface HomeScreenAction {
    object OnProfileClick : HomeScreenAction
    data object OnNavigationDrawerClick : HomeScreenAction {
        data object OnDrawerBlogsClick : HomeScreenAction
        data object OnDrawerCodelabsClick : HomeScreenAction
        data object OnDrawerPlCodingClick : HomeScreenAction
        data object OnDrawerHelpClick : HomeScreenAction
        data object OnDrawerSettingsClick : HomeScreenAction
    }
    data class OnItemClick(val id: String) : HomeScreenAction
    data class OnSearchQueryChange(val query: String) : HomeScreenAction
    data object ClearSearch : HomeScreenAction
    data class OnLeetcodeItemClick(val id: String) : HomeScreenAction
}