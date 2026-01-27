package com.example.allinone.navigation.screen

import kotlinx.serialization.Serializable

@Serializable
sealed class CodeLabScreens(val route: String) {

    @Serializable
    object ComposeArticleScreen : CodeLabScreens("compose_article")

    @Serializable
    object TaskManagerScreen : CodeLabScreens("task_manager")

    @Serializable
    object Quadrant : CodeLabScreens("quadrant")

    @Serializable
    object TipCalculator : CodeLabScreens("tip_calculator")

    @Serializable
    object Lemonade : CodeLabScreens("lemonade")

    @Serializable
    object ArtSpace : CodeLabScreens("art_space")

    @Serializable
    object BusinessCard : CodeLabScreens("business_card")

    @Serializable
    object DiceRoller : CodeLabScreens("dice_roller")

    @Serializable
    object Woof: CodeLabScreens("woof")

    @Serializable
    object PLCoding: CodeLabScreens("pl-coding") {

        @Serializable
        object MiniChallenges: CodeLabScreens("mini_challenges") {

            @Serializable
            object December: CodeLabScreens("december_mini_challenge") {

                @Serializable
                object WinterGreetingEditor: CodeLabScreens("winter_greeting_editor")

            }
            @Serializable
            object July: CodeLabScreens("july_mini_challenge"){

                @Serializable
                object CollapsibleChatThread: CodeLabScreens("collapsible_chat_thread")
            }
        }
        @Serializable
        object AppChallenges: CodeLabScreens("app_challenges")
    }

}