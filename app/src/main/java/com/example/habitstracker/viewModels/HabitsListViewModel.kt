package com.example.habitstracker.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habitstracker.HabitInfo
import com.example.habitstracker.repository.IHabitsRepository

class HabitsListViewModel(private val model: IHabitsRepository) : ViewModel() {
    private val mutableHabitInfos: MutableLiveData<ArrayList<HabitInfo>> = MutableLiveData()
    val habitInfos: LiveData<ArrayList<HabitInfo>> = mutableHabitInfos

    init {
        load()
    }
    private fun load(){
        mutableHabitInfos.value = (model.getHabits())
    }

    fun notifyItemsChanged(){
        load()
    }
}