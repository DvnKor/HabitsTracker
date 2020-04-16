package com.example.habitstracker.repository

import com.example.habitstracker.HabitInfo
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class InMemoryHabitsRepository : IHabitsRepository {
    private val habitInfos = HashMap<UUID, HabitInfo>()

    override fun getHabits(): ArrayList<HabitInfo> {
        return ArrayList(habitInfos.values)
    }

    override fun insertOrUpdate(habitInfo: HabitInfo) {
        habitInfos[habitInfo.id] = habitInfo
    }
}