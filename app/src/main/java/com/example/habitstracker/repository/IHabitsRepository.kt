package com.example.habitstracker.repository

import com.example.habitstracker.HabitInfo
import java.util.*
import kotlin.collections.ArrayList

interface IHabitsRepository {
    fun addHabit(habitInfo: HabitInfo)
    fun getHabits() : ArrayList<HabitInfo>
    fun getPositiveHabits() : ArrayList<HabitInfo>
    fun getNegativeHabits() : ArrayList<HabitInfo>
    fun changeHabit(id: UUID, habitInfo: HabitInfo)
}