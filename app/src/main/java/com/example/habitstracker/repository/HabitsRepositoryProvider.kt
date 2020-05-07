package com.example.habitstracker.repository

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.habitstracker.BuildConfig
import com.example.habitstracker.database.HabitsDatabase
import com.example.habitstracker.models.HabitInfo
import com.example.habitstracker.network.HabitServiceProvider
import com.google.gson.JsonParser
import kotlinx.coroutines.*
import retrofit2.Response
import java.util.*
import kotlin.coroutines.CoroutineContext


class HabitsRepositoryProvider {
    companion object {
        private var instance: IHabitsRepository? = null
        fun getInstance(context: Context): IHabitsRepository {
            if (instance == null) {
                val lifecycleOwner = context as LifecycleOwner
                instance = HabitsRepository(context, lifecycleOwner)
            }
            return instance!!
        }
    }

    private class HabitsRepository(context: Context, private val lifecycleOwner: LifecycleOwner) :
        IHabitsRepository, CoroutineScope {
        private val habitsDatabaseDao = HabitsDatabase.getInstance(context).habitsDao()
        private val habitService = HabitServiceProvider.getInstance()
        private val MAX_RETRIES = 10
        private val RETRY_TIMEOUT_MS: Long = 1000
        private val job = SupervisorJob()
        private val apiKey = BuildConfig.DT_API_KEY
        private val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        private var isLocalSynced = false;

        override val coroutineContext: CoroutineContext
            get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

        init {
            launch { syncHabits() }
        }


        override fun getHabits(): LiveData<List<HabitInfo>> {
            return habitsDatabaseDao.getHabits()
        }

        override fun getPositiveHabits(): LiveData<List<HabitInfo>> {
            return habitsDatabaseDao.getPositiveHabits()
        }

        override fun getNegativeHabits(): LiveData<List<HabitInfo>> {
            return habitsDatabaseDao.getNegativeHabits()
        }

        override fun getHabitById(uuid: UUID): HabitInfo? {
            return habitsDatabaseDao.getHabitById(uuid)
        }

        override fun insertOrUpdate(habitInfo: HabitInfo) {
            syncHabit(habitInfo)
            habitsDatabaseDao.insertOrUpdate(habitInfo)
        }

        override fun insert(habitInfo: HabitInfo) {
            syncHabit(habitInfo)
            habitsDatabaseDao.insert(habitInfo)
        }

        override fun update(habitInfo: HabitInfo) {
            syncHabit(habitInfo)
            habitsDatabaseDao.update(habitInfo)
        }

        override fun delete(uuid: UUID) {
            launch {
                withContext(Dispatchers.IO) {
                    deleteHabitFromNetwork(uuid)
                    habitsDatabaseDao.delete(uuid)
                }
            }
        }

        private fun syncHabit(habitInfo: HabitInfo) {
            launch {
                withContext(Dispatchers.IO) {
                    if (habitInfo.isSynced)
                        putHabitToNetwork(habitInfo)
                    else
                        syncLocalUnsavedHabit(habitInfo)
                }
            }
        }

        private suspend fun <T> processMethod(method: suspend () -> Response<T>): T? {
            for (retryNumber in 0..MAX_RETRIES) {
                var response: Response<T>? = null
                val isConnected =
                    connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
                if (isConnected) {
                    withContext(Dispatchers.IO) {
                        response = method()
                    }
                    if (response?.code() == 200) {
                        return response?.body()
                    } else {
                        Log.e(
                            "network",
                            JsonParser().parse(
                                response?.errorBody()?.string()
                            ).asJsonObject.get("message").asString
                        )
                    }
                }
                delay(RETRY_TIMEOUT_MS)
            }
            return null
        }


        private suspend fun getHabitsFromNetwork(): List<HabitInfo>? {
            return processMethod { habitService.getHabits(apiKey) }
        }

        private suspend fun putHabitToNetwork(habit: HabitInfo): UUID? {
            return processMethod { habitService.putHabit(apiKey, habit) }
        }

        private suspend fun deleteHabitFromNetwork(uuid: UUID): UUID? {
            return processMethod { habitService.deleteHabit(apiKey, uuid) }
        }


        private suspend fun syncHabits() {
            val localHabits = habitsDatabaseDao.getHabits()
            val savedHabits = getHabitsFromNetwork()
            if (savedHabits != null) {
                for (habit in savedHabits) {
                    withContext(Dispatchers.IO) { habitsDatabaseDao.insertOrUpdate(habit) }
                }
            }
            localHabits.observe(lifecycleOwner, Observer { habitInfos ->
                if (!isLocalSynced) {
                    for (habit in habitInfos) {
                        launch {
                            withContext(Dispatchers.IO) {
                                syncLocalUnsavedHabit(habit)
                            }
                        }
                    }
                    isLocalSynced = true
                }
            })

        }

        private suspend fun syncLocalUnsavedHabit(habit: HabitInfo) {
            if (!habit.isSynced) {
                val newHabitUUID = putHabitToNetwork(habit)
                if (newHabitUUID != null) {
                    val newHabit = HabitInfo(
                        newHabitUUID,
                        habit.name,
                        habit.description,
                        habit.type,
                        habit.numberOfRepeats,
                        habit.numberOfDays,
                        habit.color,
                        habit.priority,
                        isSynced = true
                    )
                    withContext(Dispatchers.IO) {
                        habitsDatabaseDao.delete(habit.id)
                        habitsDatabaseDao.insert(newHabit)
                    }
                }
            }
        }
    }
}

