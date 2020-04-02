package com.example.habitstracker

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


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

}