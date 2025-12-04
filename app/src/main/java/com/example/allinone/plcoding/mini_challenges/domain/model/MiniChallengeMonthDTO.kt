package com.example.allinone.plcoding.mini_challenges.domain.model

data class MiniChallengeMonthDTO(
    val month: String,
    val topic: String? = ""
)

val months = listOf(
    MiniChallengeMonthDTO("February"),
    MiniChallengeMonthDTO("March","Space"),
    MiniChallengeMonthDTO("April","Easter"),
    MiniChallengeMonthDTO("May","Learning & Study Tools"),
    MiniChallengeMonthDTO("June", "Birthday Celebrations"),
    MiniChallengeMonthDTO("July", "Conversations"),
    MiniChallengeMonthDTO("August", "Async Adventures"),
    MiniChallengeMonthDTO("September", "Designing the Festival"),
    MiniChallengeMonthDTO("October", "Android Halloween Lab"),
    MiniChallengeMonthDTO("November", "Black Friday Madness"),
    MiniChallengeMonthDTO("December", ""),
)