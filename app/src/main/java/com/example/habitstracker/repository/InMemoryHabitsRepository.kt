package com.example.habitstracker.repository

import com.example.habitstracker.HabitInfo

class InMemoryHabitsRepository : IHabitsRepository {
    private val habitInfos = arrayListOf<HabitInfo>()
    override fun addHabit(habitInfo: HabitInfo) {
        habitInfos.add(habitInfo)
    }

    override fun getPositiveHabits() : ArrayList<HabitInfo> {
        //TODO: enum
        return ArrayList(habitInfos.filter { habitInfo -> habitInfo.type == "Позитивная" })
    }

    override fun getNegativeHabits(): ArrayList<HabitInfo> {
        return ArrayList(habitInfos.filter { habitInfo -> habitInfo.type == "Позитивная" })
    }

    override fun changeHabit() {
        TODO("Not yet implemented")
    }
}