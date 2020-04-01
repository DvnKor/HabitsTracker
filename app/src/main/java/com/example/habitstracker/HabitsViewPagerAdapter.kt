package com.example.habitstracker

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.ListFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.android.synthetic.main.habit_info_view.view.*


class HabitsViewPagerAdapter(
    private val positiveHabitInfos: ArrayList<HabitInfo>,
    private val negativeHabitInfos: ArrayList<HabitInfo>,
    private val parent: Fragment,
    private val negativeListFragment: HabitListFragment = HabitListFragment.newInstance(
        negativeHabitInfos
    )
    ,
    private val positiveListFragment: HabitListFragment = HabitListFragment.newInstance(
        positiveHabitInfos
    )
) :
    FragmentStateAdapter(parent) {

    override fun getItemCount() = 2
    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            positiveListFragment
        } else {
            negativeListFragment
        }
    }

    fun addHabitInfo(habitInfo: HabitInfo) {
        if (habitInfo.type == "Позитивная")
            positiveListFragment.addHabitInfo(habitInfo)
        else
            negativeListFragment.addHabitInfo(habitInfo)
    }

    fun changeHabitInfo(habitInfoPosition: Int, habitInfo: HabitInfo) {
        if (habitInfo.type == "Позитивная")
            positiveListFragment.changeHabitInfo(habitInfoPosition, habitInfo)
        else
            negativeListFragment.changeHabitInfo(habitInfoPosition, habitInfo)
    }

}