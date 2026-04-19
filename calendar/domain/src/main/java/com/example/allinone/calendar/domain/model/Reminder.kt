package com.example.allinone.calendar.domain.model

data class Reminder(
    val id: Long = 0L,
    val title: String,
    val description: String? = null,
    val triggerAtMillis: Long,
    val leadTime: LeadTime = LeadTime.AT_TIME,
    val sourceId: String? = null,
) {
    val fireAtMillis: Long get() = triggerAtMillis - leadTime.minutes * 60_000L
}