package com.example.domain.model

data class Challenge(
    val id: String,
    val title: String
)

fun getChallengesForMonth(month: String): List<Challenge> {
    return when (month.lowercase()) {
        "december" -> listOf(
            Challenge("holiday_gift_order", "Holiday Gift Order"),
            Challenge("santa_clap_piano", "Santa Clap Piano"),
            Challenge("snow_globe_shake", "Snow Globe Shake"),
            Challenge("holiday_cashback_activation", "Holiday Cashback Activation"),
            Challenge("winter_greeting_editor", "Winter Greeting Editor")
        )
        "july" -> listOf(
            Challenge("challenge_1", "Challenge 1"),
            Challenge("challenge_2", "Challenge 2"),
            Challenge("challenge_3", "Challenge 3"),
            Challenge("challenge_4", "Challenge 4"),
            Challenge("collapsible_chat_thread", "Collapsible Chat Thread")
        )
        else -> listOf(
            Challenge("challenge_1", "Challenge 1"),
            Challenge("challenge_2", "Challenge 2"),
            Challenge("challenge_3", "Challenge 3"),
            Challenge("challenge_4", "Challenge 4"),
            Challenge("challenge_5", "Challenge 5")
        )
    }
}