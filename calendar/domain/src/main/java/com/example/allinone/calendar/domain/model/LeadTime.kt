package com.example.allinone.calendar.domain.model

enum class LeadTime(val minutes: Int) {
    AT_TIME(0),
    FIVE_MINUTES(5),
    TEN_MINUTES(10),
    THIRTY_MINUTES(30),
    ONE_HOUR(60),
    ONE_DAY(24 * 60);

    companion object {
        fun fromMinutes(minutes: Int): LeadTime =
            entries.firstOrNull { it.minutes == minutes } ?: AT_TIME
    }
}