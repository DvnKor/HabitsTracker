package com.example.habitstracker.repository

import androidx.lifecycle.LiveData
import com.example.habitstracker.models.HabitInfo
import java.util.*

interface IHabitsRepository {
    fun getHabits(): LiveData<List<HabitInfo>>
    fun getPositiveHabits(): LiveData<List<HabitInfo>>
    fun getNegativeHabits(): LiveData<List<HabitInfo>>
    fun getHabitById(uuid: UUID): HabitInfo?
    fun insertOrUpdate(habitInfo: HabitInfo)
    fun insert(habitInfo: HabitInfo)
    fun update(habitInfo: HabitInfo)
    fun delete(uuid: UUID)
}