package com.example.habitstracker.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.habitstracker.IHabitChangedCallback
import com.example.habitstracker.models.HabitInfo
import com.example.habitstracker.repository.HabitsDatabase
import com.example.habitstracker.repository.IHabitsRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class HabitEditingViewModel(
    context: Context
) : ViewModel(), CoroutineScope {

    private val db: HabitsDatabase = HabitsDatabase.getInstance(context)
    private val repository: IHabitsRepository = db.habitsDao()
    private val job = SupervisorJob()

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }

    fun changeHabit(habitInfo: HabitInfo, habitChangedCallback: IHabitChangedCallback) = launch {
        withContext(Dispatchers.IO) {
            repository.insertOrUpdate(habitInfo)
            habitChangedCallback.onHabitChanged()//TODO : callback после insert'a *СДЕЛАНО*
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }
}