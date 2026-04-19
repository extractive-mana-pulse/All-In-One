package com.example.allinone.calendar.data.local

import com.example.allinone.calendar.domain.model.Reminder
import com.example.allinone.calendar.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReminderRepositoryImpl(
    private val dao: ReminderDao,
) : ReminderRepository {

    override fun observeAll(): Flow<List<Reminder>> =
        dao.observeAll().map { list -> list.map(ReminderEntity::toDomain) }

    override suspend fun get(id: Long): Reminder? = dao.get(id)?.toDomain()

    override suspend fun upsert(reminder: Reminder): Long {
        val entity = ReminderEntity.fromDomain(reminder)
        return if (reminder.id == 0L) {
            dao.insert(entity)
        } else {
            dao.update(entity)
            reminder.id
        }
    }

    override suspend fun delete(id: Long) = dao.delete(id)
}