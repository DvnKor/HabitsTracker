package com.example.habitstracker.repository

import androidx.lifecycle.LiveData
import com.example.habitstracker.models.HabitInfo

interface IHabitsRepository {
    fun getHabits() : LiveData<List<HabitInfo>>
    fun getPositiveHabits() : LiveData<List<HabitInfo>>
    fun getNegativeHabits() : LiveData<List<HabitInfo>>
    fun insertOrUpdate(habitInfo: HabitInfo)
}