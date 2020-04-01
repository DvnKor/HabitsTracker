package com.example.habitstracker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.core.view.children
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_habit_editing.*
import kotlinx.android.synthetic.main.fragment_habit_list.*
import kotlin.math.round

class HabitListFragment : Fragment() {
    companion object {
        private const val habitInfosArgName = "habitInfos"
        fun newInstance(
            habitInfos: ArrayList<HabitInfo> = arrayListOf()
        ): HabitListFragment {
            val fragment = HabitListFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList(habitInfosArgName, habitInfos)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var habitInfos: ArrayList<HabitInfo> = arrayListOf()
    private var habitChangedCallback: IHabitChangedCallback? = null
    private lateinit var viewAdapter: HabitsRecyclerViewAdapter
    override fun onAttach(context: Context) {
        super.onAttach(context)
        habitChangedCallback = activity as IHabitChangedCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habit_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            habitInfos = it.getParcelableArrayList(habitInfosArgName) ?: arrayListOf()
        }
        val viewManager = LinearLayoutManager(context)
        viewAdapter = HabitsRecyclerViewAdapter(habitInfos, activity!!.supportFragmentManager)
        habitsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
    fun addHabitInfo(habitInfo: HabitInfo){
        habitInfos.add(habitInfo)
        viewAdapter.notifyDataSetChanged()
    }
    fun changeHabitInfo(habitInfoPosition: Int, habitInfo: HabitInfo) {
        habitInfos[habitInfoPosition] = habitInfo
        viewAdapter.notifyDataSetChanged()
    }


}