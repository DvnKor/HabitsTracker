package com.example.habitstracker

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.habit_info_view.view.*

class HabitsRecyclerViewAdapter(
    private val habitsInfos: MutableList<HabitInfo>,
    private val fragmentManager: FragmentManager
) :
    RecyclerView.Adapter<HabitsRecyclerViewAdapter.HabitViewHolder>() {

    class HabitViewHolder(
        private val view: View,
        private val context: Context,
        private val fragmentManager: FragmentManager,
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
            fragmentManager.beginTransaction()
                .replace(R.id.mainLayout, HabitEditingFragment.newInstance(position, habitInfo))
                .commit()
//            val intent =
//                Intent(context, HabitEditingActivity::class.java).putExtra("habitInfo", habitInfo)
//                    .putExtra("habitInfoPosition", position)
//            (context as MainActivity).startActivityForResult(intent, context.changeHabitRequestCode)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HabitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.habit_info_view, parent, false)
        return HabitViewHolder(view, parent.context, fragmentManager, null)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(habitsInfos[position])
        holder.habitInfo = habitsInfos[position]
        holder.position = position

    }

    override fun getItemCount() = habitsInfos.size

}