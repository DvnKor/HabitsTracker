package com.example.habitstracker.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habitstracker.HabitInfo
import java.util.*

@Dao
interface RoomHabitsDao : IHabitsRepository {
    @Query("SELECT * FROM HabitInfo")
    override fun getHabits(): LiveData<List<HabitInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertOrUpdate(habitInfo: HabitInfo)
}