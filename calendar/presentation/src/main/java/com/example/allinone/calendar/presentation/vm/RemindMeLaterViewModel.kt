package com.example.allinone.calendar.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinone.calendar.data.external.CalendarAppLauncher
import com.example.allinone.calendar.data.external.ResolvedCalendarApp
import com.example.allinone.calendar.data.notification.ReminderScheduler
import com.example.allinone.calendar.domain.model.LeadTime
import com.example.allinone.calendar.domain.model.Reminder
import com.example.allinone.calendar.domain.repository.ReminderRepository
import com.example.allinone.calendar.domain.repository.ReminderSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class RemindMeLaterUiState(
    val step: Step = Step.DATE,
    val selectedDateMillis: Long? = null,
    val selectedHour: Int = 9,
    val selectedMinute: Int = 0,
    val leadTime: LeadTime = LeadTime.AT_TIME,
    val calendarApps: List<ResolvedCalendarApp> = emptyList(),
    val isComplete: Boolean = false,
) {
    enum class Step { DATE, TIME, LEAD, DESTINATION }

    val canProceedFromDate: Boolean get() = selectedDateMillis != null

    fun triggerAtMillis(): Long? {
        val date = selectedDateMillis ?: return null
        return Calendar.getInstance().apply {
            timeInMillis = date
            set(Calendar.HOUR_OF_DAY, selectedHour)
            set(Calendar.MINUTE, selectedMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }
}

@HiltViewModel
class RemindMeLaterViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository,
    private val settingsRepository: ReminderSettingsRepository,
    private val scheduler: ReminderScheduler,
    private val calendarAppLauncher: CalendarAppLauncher,
) : ViewModel() {

    private var title: String = ""
    private var description: String? = null
    private var sourceId: String? = null

    private val _state = MutableStateFlow(RemindMeLaterUiState())
    val state: StateFlow<RemindMeLaterUiState> = _state.asStateFlow()

    fun open(title: String, description: String? = null, sourceId: String? = null) {
        this.title = title
        this.description = description
        this.sourceId = sourceId
        viewModelScope.launch {
            val defaultLead = settingsRepository.settings.first().defaultLeadTime
            _state.value = RemindMeLaterUiState(leadTime = defaultLead)
        }
    }

    fun onDateSelected(dateMillis: Long?) {
        _state.update { it.copy(selectedDateMillis = dateMillis) }
    }

    fun onTimeSelected(hour: Int, minute: Int) {
        _state.update { it.copy(selectedHour = hour, selectedMinute = minute) }
    }

    fun onLeadTimeSelected(leadTime: LeadTime) {
        _state.update { it.copy(leadTime = leadTime) }
    }

    fun nextStep() {
        _state.update { current ->
            val next = when (current.step) {
                RemindMeLaterUiState.Step.DATE -> RemindMeLaterUiState.Step.TIME
                RemindMeLaterUiState.Step.TIME -> RemindMeLaterUiState.Step.LEAD
                RemindMeLaterUiState.Step.LEAD -> RemindMeLaterUiState.Step.DESTINATION
                RemindMeLaterUiState.Step.DESTINATION -> RemindMeLaterUiState.Step.DESTINATION
            }
            val apps = if (next == RemindMeLaterUiState.Step.DESTINATION) {
                calendarAppLauncher.installedCalendarApps()
            } else current.calendarApps
            current.copy(step = next, calendarApps = apps)
        }
    }

    fun previousStep() {
        _state.update { current ->
            val prev = when (current.step) {
                RemindMeLaterUiState.Step.DATE -> RemindMeLaterUiState.Step.DATE
                RemindMeLaterUiState.Step.TIME -> RemindMeLaterUiState.Step.DATE
                RemindMeLaterUiState.Step.LEAD -> RemindMeLaterUiState.Step.TIME
                RemindMeLaterUiState.Step.DESTINATION -> RemindMeLaterUiState.Step.LEAD
            }
            current.copy(step = prev)
        }
    }

    fun chooseLocalReminder() {
        val triggerAt = _state.value.triggerAtMillis() ?: return
        viewModelScope.launch {
            val reminder = Reminder(
                title = title,
                description = description,
                triggerAtMillis = triggerAt,
                leadTime = _state.value.leadTime,
                sourceId = sourceId,
            )
            val id = reminderRepository.upsert(reminder)
            scheduler.schedule(reminder.copy(id = id))
            _state.update { it.copy(isComplete = true) }
        }
    }

    fun chooseCalendarApp(app: ResolvedCalendarApp) {
        val triggerAt = _state.value.triggerAtMillis() ?: return
        val reminder = Reminder(
            title = title,
            description = description,
            triggerAtMillis = triggerAt,
            leadTime = _state.value.leadTime,
            sourceId = sourceId,
        )
        calendarAppLauncher.launchIn(app, reminder)
        _state.update { it.copy(isComplete = true) }
    }
}
