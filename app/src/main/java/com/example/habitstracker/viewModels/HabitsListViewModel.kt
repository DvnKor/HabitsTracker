package com.example.habitstracker.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habitstracker.HabitInfo
import com.example.habitstracker.repository.IHabitsRepository

class HabitsListViewModel(private val model: IHabitsRepository) : ViewModel() {
    private lateinit var originalHabitInfos: ArrayList<HabitInfo>
    private val mutableHabitInfos: MutableLiveData<ArrayList<HabitInfo>> = MutableLiveData()
    val habitInfos: LiveData<ArrayList<HabitInfo>> = mutableHabitInfos

    init {
        load()
    }

    private fun load() {
        val habits = model.getHabits()
        mutableHabitInfos.value = ArrayList(habits)
        originalHabitInfos = ArrayList(habits)
    }

    fun searchByName(name: String) {
        mutableHabitInfos.value =
            ArrayList(originalHabitInfos.filter { habitInfo -> habitInfo.name.startsWith(name) })
    }

    fun sortByName(ascending: Boolean) {
        if (ascending)
            mutableHabitInfos.value = ArrayList(mutableHabitInfos.value!!.sortedBy { it.name })
        else
            mutableHabitInfos.value =
                ArrayList(mutableHabitInfos.value!!.sortedByDescending { it.name })
    }


    fun notifyItemsChanged() {
        load()
    }
}