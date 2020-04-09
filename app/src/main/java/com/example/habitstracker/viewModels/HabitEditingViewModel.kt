package com.example.habitstracker.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habitstracker.HabitInfo
import com.example.habitstracker.repository.IHabitsRepository
import java.util.*
import kotlin.collections.ArrayList

class HabitEditingViewModel(private val model: IHabitsRepository, private val habitsListViewModel: HabitsListViewModel) : ViewModel() {
    private val mutableHabitInfos: MutableLiveData<ArrayList<HabitInfo>> = MutableLiveData()
    val habitInfos: LiveData<ArrayList<HabitInfo>> = mutableHabitInfos

    private val mutablePositiveHabitInfos: MutableLiveData<ArrayList<HabitInfo>> = MutableLiveData()
    val positiveHabitInfos: LiveData<ArrayList<HabitInfo>> = mutablePositiveHabitInfos

    private val mutableNegativeHabitInfos: MutableLiveData<ArrayList<HabitInfo>> = MutableLiveData()
    val negativeHabitInfos: LiveData<ArrayList<HabitInfo>> = mutableNegativeHabitInfos

    init {
        load()
    }
    private fun load(){
        mutableHabitInfos.postValue(model.getHabits())
    }

    fun changeHabit(habitInfo: HabitInfo){
        model.changeHabit(habitInfo.id, habitInfo)
        habitsListViewModel.notifyItemsChanged()
    }
}