package com.example.habitstracker.network

import com.example.habitstracker.models.HabitInfo
import com.example.habitstracker.models.HabitJsonDeserializer
import com.example.habitstracker.models.HabitJsonSerializer
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HabitServiceProvider {
    companion object {
        private const val BASE_URL = "droid-test-server.doubletapp.ru/api"
        private var instance: IHabitService? = null
        fun getInstance(): IHabitService {
            val gson = GsonBuilder()
                .registerTypeAdapter(HabitInfo::class.java, HabitJsonSerializer())
                .registerTypeAdapter(HabitInfo::class.java, HabitJsonDeserializer())
                .create()
            if (instance == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build()
                instance = retrofit.create(IHabitService::class.java)
            }
            return instance!!
        }
    }
}