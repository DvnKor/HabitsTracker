package com.example.habitstracker.repository

import com.example.habitstracker.HabitInfo
import com.example.habitstracker.HabitType
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class InMemoryHabitsRepository : IHabitsRepository {
    private val habitInfos = HashMap<UUID, HabitInfo>()
    override fun addHabit(habitInfo: HabitInfo) {
        habitInfos[habitInfo.id] = habitInfo
    }

    override fun getHabits(): ArrayList<HabitInfo> {
        return ArrayList(habitInfos.values)
    }

    override fun getPositiveHabits(): ArrayList<HabitInfo> {
        return ArrayList(getHabits().filter { habitInfo -> habitInfo.type == HabitType.Positive.type })
    }

    override fun getNegativeHabits(): ArrayList<HabitInfo> {
        return ArrayList(getHabits().filter { habitInfo -> habitInfo.type == HabitType.Negative.type })
    }

    override fun changeHabit(id: UUID, habitInfo: HabitInfo) {
        habitInfos[id] = habitInfo
    }
}