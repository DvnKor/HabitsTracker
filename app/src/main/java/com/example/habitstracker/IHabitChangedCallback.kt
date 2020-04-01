package com.example.habitstracker

import java.text.FieldPosition

interface IHabitChangedCallback {
    fun onHabitChanged(
        position: Int?,
        habitInfo: HabitInfo?,
        oldHabitInfo: HabitInfo?,
        oldPosition: Int?
    )
}