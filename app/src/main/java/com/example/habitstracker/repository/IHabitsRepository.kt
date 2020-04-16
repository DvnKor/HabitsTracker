package com.example.habitstracker.repository

import androidx.lifecycle.LiveData
import com.example.habitstracker.HabitInfo

interface IHabitsRepository {
    fun getHabits() : LiveData<List<HabitInfo>>
    fun insertOrUpdate(habitInfo: HabitInfo)
}