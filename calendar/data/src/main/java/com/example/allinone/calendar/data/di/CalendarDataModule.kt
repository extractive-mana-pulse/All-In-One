package com.example.allinone.calendar.data.di

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import androidx.room.Room
import com.example.allinone.calendar.data.external.CalendarAppLauncher
import com.example.allinone.calendar.data.local.ReminderDao
import com.example.allinone.calendar.data.local.ReminderDatabase
import com.example.allinone.calendar.data.local.ReminderRepositoryImpl
import com.example.allinone.calendar.data.local.ReminderSettingsRepositoryImpl
import com.example.allinone.calendar.data.notification.ReminderNotifier
import com.example.allinone.calendar.data.notification.ReminderScheduler
import com.example.allinone.calendar.domain.repository.ReminderRepository
import com.example.allinone.calendar.domain.repository.ReminderSettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CalendarDataModule {

    @Provides
    @Singleton
    fun provideReminderDatabase(@ApplicationContext context: Context): ReminderDatabase =
        Room.databaseBuilder(context, ReminderDatabase::class.java, "reminders.db").build()

    @Provides
    fun provideReminderDao(database: ReminderDatabase): ReminderDao = database.reminderDao()

    @Provides
    @Singleton
    fun provideReminderRepository(dao: ReminderDao): ReminderRepository =
        ReminderRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideReminderSettingsRepository(
        @ApplicationContext context: Context,
    ): ReminderSettingsRepository = ReminderSettingsRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideAlarmManager(@ApplicationContext context: Context): AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    @Provides
    @Singleton
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @Provides
    @Singleton
    fun provideReminderScheduler(
        @ApplicationContext context: Context,
        alarmManager: AlarmManager,
    ): ReminderScheduler = ReminderScheduler(context, alarmManager)

    @Provides
    @Singleton
    fun provideReminderNotifier(
        @ApplicationContext context: Context,
        notificationManager: NotificationManager,
    ): ReminderNotifier = ReminderNotifier(context, notificationManager)

    @Provides
    @Singleton
    fun provideCalendarAppLauncher(@ApplicationContext context: Context): CalendarAppLauncher =
        CalendarAppLauncher(context)
}
