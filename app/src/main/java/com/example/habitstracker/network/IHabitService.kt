package com.example.habitstracker.network

import com.example.habitstracker.models.HabitInfo
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface IHabitService {

    //@Headers("Authorization:ab78216d-cc6f-4578-b58b-a7c8e4eb81ef")
    @GET("/habit")
    suspend fun getHabits(@Header("Authorization") authorization : String): List<HabitInfo>

   // @Headers("Authorization:ab78216d-cc6f-4578-b58b-a7c8e4eb81ef")
    @PUT("/habit")
    suspend fun putHabits(@Header("Authorization") authorization : String, @Body habit: HabitInfo) : Call<String>

    //@Headers("Authorization:ab78216d-cc6f-4578-b58b-a7c8e4eb81ef")
    @DELETE("/habit")
    suspend fun deleteHabit(@Header("Authorization") authorization : String, @Body uid: UUID) : Call<String>
}