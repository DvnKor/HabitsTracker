package com.example.habitstracker.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.habitstracker.HabitInfo

@Database(entities = [HabitInfo::class], version = 1)
abstract class HabitsDatabase : RoomDatabase() {
    abstract fun habitsDao(): RoomHabitsDao
}