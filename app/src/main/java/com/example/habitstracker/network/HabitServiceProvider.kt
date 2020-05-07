package com.example.habitstracker.network

import com.example.habitstracker.models.*
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class HabitServiceProvider {
    companion object {
        private const val BASE_URL = "https://droid-test-server.doubletapp.ru/"
        private var instance: IHabitService? = null
        fun getInstance(): IHabitService {
            val gson = GsonBuilder()
                .registerTypeAdapter(HabitInfo::class.java, HabitJsonSerializer())
                .registerTypeAdapter(HabitInfo::class.java, HabitJsonDeserializer())
                .registerTypeAdapter(UUID::class.java, UUIDJsonSerializer())
                .registerTypeAdapter(UUID::class.java, UUIDJsonDeserializer())
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