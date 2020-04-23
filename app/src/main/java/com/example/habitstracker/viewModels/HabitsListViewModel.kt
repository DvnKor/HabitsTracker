package com.example.habitstracker.viewModels

import android.content.Context
import androidx.lifecycle.*
import com.example.habitstracker.models.HabitInfo
import com.example.habitstracker.repository.HabitsDatabase

class HabitsListViewModel(
    context: Context,
    lifecycleOwner: LifecycleOwner
) : ViewModel() {

    private lateinit var originalPositiveHabitInfos: List<HabitInfo>
    private val mutablePositiveHabitInfos: MutableLiveData<List<HabitInfo>> = MutableLiveData()
    val positiveHabitInfos: LiveData<List<HabitInfo>> = mutablePositiveHabitInfos

    private lateinit var originalNegativeHabitInfos: List<HabitInfo>
    private val mutableNegativeHabitInfos: MutableLiveData<List<HabitInfo>> = MutableLiveData()
    val negativeHabitInfos: LiveData<List<HabitInfo>> = mutableNegativeHabitInfos

    private var nameToSearch: String = ""
    private var isAscending: Boolean = true

    init {
        val db = HabitsDatabase.getInstance(context)
        val repository = db.habitsDao()

        repository.getPositiveHabits().observe(lifecycleOwner, Observer { habitInfos ->
            originalPositiveHabitInfos = habitInfos
            mutablePositiveHabitInfos.postValue(
                sortListByName(
                    isAscending,
                    filterByName(nameToSearch, habitInfos)
                )
            )
        })


        repository.getNegativeHabits().observe(lifecycleOwner, Observer { habitInfos ->
            originalNegativeHabitInfos = habitInfos
            mutableNegativeHabitInfos.postValue(
                sortListByName(
                    isAscending,
                    filterByName(nameToSearch, habitInfos)
                )
            )
        })
    }

    fun searchByName(name: String) {
        nameToSearch = name
        mutablePositiveHabitInfos.postValue(filterByName(name, originalPositiveHabitInfos))
        mutableNegativeHabitInfos.postValue(filterByName(name, originalNegativeHabitInfos))
    }

    fun sortByName(isAscending: Boolean) {
        this.isAscending = isAscending
        mutablePositiveHabitInfos.postValue(
            sortListByName(
                isAscending,
                mutablePositiveHabitInfos.value!!
            )
        )
        mutableNegativeHabitInfos.postValue(
            sortListByName(
                isAscending,
                mutableNegativeHabitInfos.value!!
            )
        )
    }

    private fun filterByName(name: String, habitInfos: List<HabitInfo>): List<HabitInfo> {
        return habitInfos.filter { habitInfo -> habitInfo.name.startsWith(name) }
    }

    private fun sortListByName(isAscending: Boolean, habitInfos: List<HabitInfo>): List<HabitInfo> {
        return if (isAscending)
            habitInfos.sortedBy { it.name }
        else
            habitInfos.sortedByDescending { it.name }
    }
}