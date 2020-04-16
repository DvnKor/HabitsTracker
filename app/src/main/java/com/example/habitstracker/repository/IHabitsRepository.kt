package com.example.habitstracker.repository

import com.example.habitstracker.HabitInfo

interface IHabitsRepository {
    fun getHabits() : List<HabitInfo>
    fun insertOrUpdate(habitInfo: HabitInfo)
}