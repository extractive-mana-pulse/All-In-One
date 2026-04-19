package com.example.allinone.calendar.domain.model

data class ReminderSettings(
    val notificationsEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true,
    val soundEnabled: Boolean = true,
    val defaultLeadTime: LeadTime = LeadTime.AT_TIME,
)