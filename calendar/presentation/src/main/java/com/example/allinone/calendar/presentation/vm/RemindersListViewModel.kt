package com.example.allinone.calendar.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinone.calendar.data.notification.ReminderScheduler
import com.example.allinone.calendar.domain.model.Reminder
import com.example.allinone.calendar.domain.repository.ReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemindersListViewModel @Inject constructor(
    private val repository: ReminderRepository,
    private val scheduler: ReminderScheduler,
) : ViewModel() {

    val reminders: StateFlow<List<Reminder>> = repository.observeAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun cancel(id: Long) {
        viewModelScope.launch {
            scheduler.cancel(id)
            repository.delete(id)
        }
    }
}
