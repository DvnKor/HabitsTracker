package com.example.habitstracker.viewModels

import androidx.lifecycle.*
import com.example.habitstracker.HabitInfo
import com.example.habitstracker.repository.IHabitsRepository

class HabitsListViewModel(
    model: IHabitsRepository,
    lifecycleOwner: LifecycleOwner
) : ViewModel() {
    private lateinit var originalHabitInfos: List<HabitInfo>
    private val mutableHabitInfos: MutableLiveData<List<HabitInfo>> = MutableLiveData()
    val habitInfos: LiveData<List<HabitInfo>> = mutableHabitInfos
    private var nameToSearch: String = ""
    private var isAscending: Boolean = true
    init {
        model.getHabits().observe(lifecycleOwner, Observer { habitInfos ->
            originalHabitInfos = habitInfos
            mutableHabitInfos.value = sortListByName(isAscending, filterByName(nameToSearch, habitInfos))
        })
    }

    private fun filterByName(name : String, habitInfos: List<HabitInfo>) : List<HabitInfo>{
        return habitInfos.filter { habitInfo -> habitInfo.name.startsWith(name) }
    }
    private fun sortListByName(isAscending: Boolean, habitInfos: List<HabitInfo>) : List<HabitInfo>{
        return if (isAscending)
            habitInfos.sortedBy { it.name }
        else
            habitInfos.sortedByDescending { it.name }
    }
    fun searchByName(name: String) {
        nameToSearch = name
        mutableHabitInfos.value = filterByName(name, originalHabitInfos)
    }

    fun sortByName(isAscending: Boolean) {
        this.isAscending = isAscending
        mutableHabitInfos.value = sortListByName(isAscending, mutableHabitInfos.value!!)
    }
}