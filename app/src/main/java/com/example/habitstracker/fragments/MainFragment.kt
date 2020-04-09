package com.example.habitstracker.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.habitstracker.HabitInfo
import com.example.habitstracker.HabitType
import com.example.habitstracker.R
import com.example.habitstracker.adapters.HabitsViewPagerAdapter
import com.example.habitstracker.viewModels.HabitsListViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    companion object {
        private const val positiveHabitInfosArgName = "positiveHabitInfos"
        private const val negativeHabitInfosArgName = "negativeHabitInfos"
        fun newInstance(
            positiveHabitInfos: ArrayList<HabitInfo>,
            negativeHabitInfos: ArrayList<HabitInfo>
        ): MainFragment {
            val fragment = MainFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList(positiveHabitInfosArgName, positiveHabitInfos)
            bundle.putParcelableArrayList(negativeHabitInfosArgName, negativeHabitInfos)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewAdapter: HabitsViewPagerAdapter
    private var positiveHabitInfos: ArrayList<HabitInfo> = arrayListOf()
    private var negativeHabitInfos: ArrayList<HabitInfo> = arrayListOf()
    private var editingFragment: HabitEditingFragment? = null
    private val habitsTypesList = arrayListOf("Позитивные", "Негативные")
    private val habitsListViewModel: HabitsListViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener(this::onFabClick)
        arguments?.let {
            positiveHabitInfos =
                it.getParcelableArrayList(positiveHabitInfosArgName) ?: arrayListOf()
            negativeHabitInfos =
                it.getParcelableArrayList(negativeHabitInfosArgName) ?: arrayListOf()
        }
        habitsListViewModel.habitInfos.observe(viewLifecycleOwner, Observer { habitInfos ->
            viewAdapter = HabitsViewPagerAdapter(
                ArrayList(habitInfos.filter { habitInfo -> habitInfo.type == HabitType.Positive.type }),
                ArrayList(habitInfos.filter { habitInfo -> habitInfo.type == HabitType.Negative.type }),
                this
            )
            val viewPager = mainPager
            viewPager.adapter = viewAdapter
            val tabLayout = tabs
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = habitsTypesList[position]
            }.attach()
        })

    }

    private fun onFabClick(view: View) {
        editingFragment = HabitEditingFragment.newInstance()
        activity!!.findNavController(R.id.nav_host_fragment)
            .navigate(R.id.action_mainFragment_to_habitEditingFragment)

    }

}