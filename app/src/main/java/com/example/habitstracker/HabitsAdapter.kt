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


class HabitsAdapter(
    private val habitsInfos: MutableList<HabitInfo>,
    private val supportFragmentManager: FragmentManager,
    private val parentActivity: AppCompatActivity
) :
    FragmentStateAdapter(parentActivity) {

    class HabitViewHolder(
        private val view: View,
        private val context: Context,
        private val supportFragmentManager: FragmentManager,
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
            supportFragmentManager.beginTransaction()
                .add(R.id.mainLayout, HabitEditingFragment.newInstance(position, habitInfo))
                .commit()
//            val intent =
//                Intent(context, HabitEditingActivity::class.java).putExtra("habitInfo", habitInfo)
//                    .putExtra("habitInfoPosition", position)
//            (context as MainActivity).startActivityForResult(intent, context.changeHabitRequestCode)
        }

    }

//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//    viewType: Int
//    ): HabitViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.habit_info_view, parent, false)
//        return HabitViewHolder(view, parent.context, supportFragmentManager, null)
//    }
//
//    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
//        holder.bind(habitsInfos[position])
//        holder.habitInfo = habitsInfos[position]
//        holder.position = position
//
//    }

    override fun getItemCount() = 2
    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            HabitListFragment.newInstance(ArrayList(habitsInfos.filter { it.type == "Позитивная" }))
        } else {
            HabitListFragment.newInstance(ArrayList(habitsInfos.filter { it.type == "Негативная" }))
        }
    }

    fun addHabitInfo(habitInfo: HabitInfo) {
        habitsInfos.add(habitInfo)
    }

    fun changeHabitInfo(habitInfoPosition: Int, habitInfo: HabitInfo) {
        habitsInfos[habitInfoPosition] = habitInfo
    }
}