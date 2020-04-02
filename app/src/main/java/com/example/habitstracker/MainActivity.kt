package com.example.habitstracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), IHabitChangedCallback {
    private val positiveHabitInfosArgName = "positiveHabitsInfos"
    private val negativeHabitInfosArgName = "negativeHabitsInfos"
    private var positiveHabitInfos: ArrayList<HabitInfo> = arrayListOf()
    private var negativeHabitInfos: ArrayList<HabitInfo> = arrayListOf()
    private lateinit var mainFragment: MainFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            positiveHabitInfos =
                savedInstanceState.getParcelableArrayList<HabitInfo>(positiveHabitInfosArgName) as ArrayList<HabitInfo>
            negativeHabitInfos =
                savedInstanceState.getParcelableArrayList<HabitInfo>(negativeHabitInfosArgName) as ArrayList<HabitInfo>
        }
        mainFragment = MainFragment.newInstance(positiveHabitInfos, negativeHabitInfos)
        supportFragmentManager.beginTransaction().add(R.id.mainLayout, mainFragment).commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(positiveHabitInfosArgName, positiveHabitInfos)
        outState.putParcelableArrayList(negativeHabitInfosArgName, negativeHabitInfos)
    }

    private fun getArrayToChange(habitType: String): ArrayList<HabitInfo> {
        return if (habitType == "Позитивная") {
            positiveHabitInfos
        } else {
            negativeHabitInfos
        }
    }

    override fun onHabitChanged(
        position: Int?,
        habitInfo: HabitInfo?,
        oldHabitInfo: HabitInfo?,
        oldPosition: Int?
    ) {
        if (habitInfo != null) {
            if (oldHabitInfo != null && oldPosition != null && oldPosition != -1) {
                if (oldHabitInfo.type == habitInfo.type)
                    changeHabitInfo(position!!, habitInfo)
                else {
                    val from = getArrayToChange(oldHabitInfo.type)
                    val to = getArrayToChange(habitInfo.type)
                    from.removeAt(oldPosition)
                    to.add(habitInfo)
                }
            } else if (position != null && position != -1) {
                changeHabitInfo(position, habitInfo)
            } else {
                addHabitInfo(habitInfo)
            }
        }
        supportFragmentManager.beginTransaction().replace(
            R.id.mainLayout,
            MainFragment.newInstance(positiveHabitInfos, negativeHabitInfos)
        ).commit()
    }

    private fun addHabitInfo(habitInfo: HabitInfo) {
        val arrayToAdd = getArrayToChange(habitInfo.type)
        arrayToAdd.add(habitInfo)
    }

    private fun changeHabitInfo(habitInfoPosition: Int, habitInfo: HabitInfo) {
        val arrayToAdd = getArrayToChange(habitInfo.type)
        arrayToAdd[habitInfoPosition] = habitInfo

    }

}
