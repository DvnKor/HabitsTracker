package com.example.habitstracker

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), IHabitChangedCallback {
    val changeHabitRequestCode = 1
    private val positiveHabitInfosArgName = "positiveHabitsInfos"
    private val negativeHabitInfosArgName = "negativeHabitsInfos"
    private var positiveHabitInfos: ArrayList<HabitInfo> = arrayListOf()
    private var negativeHabitInfos: ArrayList<HabitInfo> = arrayListOf()
    private var editingFragment: HabitEditingFragment? = null
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


    override fun onHabitChanged(position: Int?, habitInfo: HabitInfo?) {
        if (habitInfo != null) {
            if (position != null && position != -1) {
                changeHabitInfo(position, habitInfo)
            } else {
                addHabitInfo(habitInfo)
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.mainLayout, MainFragment.newInstance(positiveHabitInfos, negativeHabitInfos))
            .commit()
    }

    fun addHabitInfo(habitInfo: HabitInfo) {
        if (habitInfo.type == "Позитивная")
            positiveHabitInfos.add(habitInfo)
        else
            negativeHabitInfos.add(habitInfo)
    }

    fun changeHabitInfo(habitInfoPosition: Int, habitInfo: HabitInfo) {
        if (habitInfo.type == "Позитивная")
            positiveHabitInfos[habitInfoPosition] = habitInfo
        else
            negativeHabitInfos[habitInfoPosition] = habitInfo
    }

}
