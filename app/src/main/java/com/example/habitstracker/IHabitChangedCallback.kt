package com.example.habitstracker

interface IHabitChangedCallback {
    fun onHabitChanged(position: Int?, habitInfo: HabitInfo?)
}