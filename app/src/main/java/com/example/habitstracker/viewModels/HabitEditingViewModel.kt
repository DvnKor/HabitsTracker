package com.example.habitstracker.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
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

    fun changeHabit(habitInfo: HabitInfo) = launch {
        withContext(Dispatchers.Default) { repository.insertOrUpdate(habitInfo) }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }
}