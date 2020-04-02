package com.example.habitstracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), IHabitChangedCallback {
    private val positiveHabitInfosArgName = "positiveHabitInfos"
    private val negativeHabitInfosArgName = "negativeHabitInfos"
    private var positiveHabitInfos: ArrayList<HabitInfo> = arrayListOf()
    private var negativeHabitInfos: ArrayList<HabitInfo> = arrayListOf()
    private lateinit var navController: NavController
    private lateinit var mainFragment: MainFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_info -> {
                    onAppInfoMenuClick()
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_habits -> {
                    onHabitsListMenuClick()
                    drawerLayout.closeDrawers()
                    true
                }

                else -> false
            }
        }
        if (savedInstanceState != null) {
            positiveHabitInfos =
                savedInstanceState.getParcelableArrayList<HabitInfo>(positiveHabitInfosArgName) as ArrayList<HabitInfo>
            negativeHabitInfos =
                savedInstanceState.getParcelableArrayList<HabitInfo>(negativeHabitInfosArgName) as ArrayList<HabitInfo>
        }
        mainFragment = MainFragment.newInstance(positiveHabitInfos, negativeHabitInfos)
        val bundle = getHabitInfosBundle()
        navController.navigate(R.id.mainFragment, bundle)
    }

    private fun getHabitInfosBundle(): Bundle {
        val bundle = Bundle()
        bundle.putParcelableArrayList(positiveHabitInfosArgName, positiveHabitInfos)
        bundle.putParcelableArrayList(negativeHabitInfosArgName, negativeHabitInfos)
        return bundle
    }

    private fun onHabitsListMenuClick() {
        navController.navigate(R.id.mainFragment, getHabitInfosBundle())
    }

    private fun onAppInfoMenuClick() {
        navController.navigate(R.id.appInfoFragment)
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
        val bundle = getHabitInfosBundle()
        navController.navigate(R.id.action_habitEditingFragment_to_mainFragment, bundle)
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
