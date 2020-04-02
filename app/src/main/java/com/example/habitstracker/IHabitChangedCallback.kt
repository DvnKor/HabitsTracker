package com.example.habitstracker

interface IHabitChangedCallback {
    fun onHabitChanged(
        position: Int?,
        habitInfo: HabitInfo?,
        oldHabitInfo: HabitInfo?,
        oldPosition: Int?
    )
}