package com.example.habitstracker.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.habitstracker.models.HabitType
import com.example.habitstracker.fragments.HabitListFragment


class HabitsViewPagerAdapter(
    private val parent: Fragment
) :
    FragmentStateAdapter(parent) {
    override fun getItemCount() = 2
    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            HabitListFragment.newInstance(
                HabitType.Positive
            )
        } else {
            HabitListFragment.newInstance(
                HabitType.Negative
            )
        }
    }

}