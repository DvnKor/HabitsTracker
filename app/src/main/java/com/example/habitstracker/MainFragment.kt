package com.example.habitstracker

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    companion object {
        private const val positiveHabitInfosArgName = "positiveHabitInfos"
        private const val negativeHabitInfosArgName = "negativeHabitInfos"
        fun newInstance(
            positiveHabitInfos: ArrayList<HabitInfo>,
            negativeHabitInfos: ArrayList<HabitInfo> ): MainFragment {
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
        }
        arguments?.let {
            negativeHabitInfos =
                it.getParcelableArrayList(negativeHabitInfosArgName) ?: arrayListOf()
        }
        viewAdapter = HabitsViewPagerAdapter(positiveHabitInfos, negativeHabitInfos, this)
        val viewPager = mainPager
        viewPager.adapter = viewAdapter
        val tabLayout = tabs
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = habitsTypesList[position]
        }.attach()
    }


//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putParcelableArrayList("habitsInfos", habitsInfos)
//    }

    private fun onFabClick(view: View) {
        editingFragment = HabitEditingFragment.newInstance()
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.mainLayout, editingFragment as HabitEditingFragment).commit()

    }

    fun changeHabitInfo(position: Int, habitInfo: HabitInfo) {
        viewAdapter.changeHabitInfo(position, habitInfo)
    }

    fun addHabitInfo(habitInfo: HabitInfo) {
        viewAdapter.addHabitInfo(habitInfo)
    }

}