package com.example.habitstracker.repository

import com.example.habitstracker.HabitInfo

interface IHabitsRepository {
    fun addHabit(habitInfo: HabitInfo)
    fun getPositiveHabits() : ArrayList<HabitInfo>
    fun getNegativeHabits() : ArrayList<HabitInfo>
    fun changeHabit()
}