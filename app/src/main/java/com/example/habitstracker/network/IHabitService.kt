package com.example.habitstracker.network

import com.example.habitstracker.models.HabitInfo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface IHabitService {

    @GET("api/habit")
    suspend fun getHabits(@Header("Authorization") authorization : String): Response<List<HabitInfo>>

    @PUT("api/habit")
    suspend fun putHabit(@Header("Authorization") authorization : String, @Body habit: HabitInfo) : Response<UUID>

    @HTTP(method = "DELETE", path = "api/habit", hasBody = true)
    suspend fun deleteHabit(@Header("Authorization") authorization : String, @Body uid: UUID) : Response<UUID>
}