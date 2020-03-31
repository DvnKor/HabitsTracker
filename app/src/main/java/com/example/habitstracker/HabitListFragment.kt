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
import kotlinx.android.synthetic.main.activity_habit_editing.*
import kotlin.math.round

class HabitListFragment : Fragment() {
    companion object {
        private const val habitInfosArgName = "habitInfos"
        fun newInstance(
            habitInfos: ArrayList<HabitInfo> = arrayListOf()
        ): HabitEditingFragment {
            val fragment = HabitEditingFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList(habitInfosArgName, habitInfos)
            fragment.arguments = bundle
            return fragment
        }
    }
    private var habitInfos : ArrayList<HabitInfo> = arrayListOf()
    private var habitChangedCallback: IHabitChangedCallback? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        habitChangedCallback = activity as IHabitChangedCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_habit_editing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { habitInfos = it.getParcelableArrayList(habitInfosArgName) ?: arrayListOf() }
    }


}