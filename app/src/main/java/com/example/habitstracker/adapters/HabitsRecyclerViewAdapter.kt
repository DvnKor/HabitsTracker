package com.example.habitstracker.adapters

import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.habitstracker.HabitInfo
import com.example.habitstracker.R
import kotlinx.android.synthetic.main.habit_info_view.view.*

class HabitsRecyclerViewAdapter(
    private val habitsInfos: MutableList<HabitInfo>,
    private val navController: NavController
) :
    RecyclerView.Adapter<HabitsRecyclerViewAdapter.HabitViewHolder>() {

    class HabitViewHolder(
        private val view: View,
        private val context: Context,
        private val navController: NavController,
        var position: Int?
    ) :
        RecyclerView.ViewHolder(view), View.OnClickListener {
        var habitInfo = HabitInfo()

        init {
            view.setOnClickListener(this)
            view.description.movementMethod = ScrollingMovementMethod()
            view.description.setOnTouchListener { view, event ->
                view.parent.parent.requestDisallowInterceptTouchEvent(true)
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_UP -> view.parent.parent
                        .requestDisallowInterceptTouchEvent(false)
                }
                false
            }
        }

        fun bind(habitInfo: HabitInfo) {
            view.name.text = habitInfo.name
            view.description.text = habitInfo.description
            view.type.text = habitInfo.type
            view.priority.text = habitInfo.priority
            view.setBackgroundColor(habitInfo.color)
            val repeatsString =
                context.resources.getQuantityString(
                    R.plurals.times,
                    habitInfo.numberOfRepeats,
                    habitInfo.numberOfRepeats
                )
            val daysString =
                context.resources.getQuantityString(
                    R.plurals.days,
                    habitInfo.numberOfDays,
                    habitInfo.numberOfDays
                )
            view.period.text = context.resources.getString(
                R.string.numberOfRepeatsInDays,
                repeatsString,
                daysString
            )
        }

        override fun onClick(v: View?) {
            val bundle = Bundle()
            bundle.putParcelable("habitInfo", habitInfo)
            bundle.putInt("position", position ?: -1)
            navController.navigate(R.id.action_mainFragment_to_habitEditingFragment, bundle)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HabitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.habit_info_view, parent, false)
        return HabitViewHolder(
            view,
            parent.context,
            navController,
            null
        )
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(habitsInfos[position])
        holder.habitInfo = habitsInfos[position]
        holder.position = position

    }

    override fun getItemCount() = habitsInfos.size

}