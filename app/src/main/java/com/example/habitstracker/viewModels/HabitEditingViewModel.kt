package com.example.habitstracker.viewModels

import androidx.lifecycle.ViewModel
import com.example.habitstracker.HabitInfo
import com.example.habitstracker.repository.IHabitsRepository

class HabitEditingViewModel(
    private val model: IHabitsRepository,
    private val habitsListViewModel: HabitsListViewModel
) : ViewModel() {

    init {
        load()
    }

    private fun load() {

    }

    fun changeHabit(habitInfo: HabitInfo) {
        model.insertOrUpdate(habitInfo)
        habitsListViewModel.notifyItemsChanged()
        habitsListViewModel.sortByName(true)
    }
}