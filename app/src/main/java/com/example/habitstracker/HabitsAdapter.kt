package com.example.habitstracker

import android.content.Context
import android.content.Intent
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.habit_info_view.view.*

class HabitsAdapter(private val habitsInfos: MutableList<HabitInfo>) :
    RecyclerView.Adapter<HabitsAdapter.HabitViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class HabitViewHolder(
        private val view: View,
        private val context: Context,
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
            //TODO: перенести в ресурс, запилить склонения
            view.period.text = "${habitInfo.numberOfRepeats} раз в ${habitInfo.numberOfDays} дней"
        }

        override fun onClick(v: View?) {
            val intent =
                Intent(context, HabitEditingActivity::class.java).putExtra("habitInfo", habitInfo)
                    .putExtra("habitInfoPosition", position)
            (context as MainActivity).startActivityForResult(intent, context.changeHabitRequestCode)
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HabitViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.habit_info_view, parent, false)
        // set the view's size, margins, paddings and layout parameters
        // ...
        return HabitViewHolder(view, parent.context, null)
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.bind(habitsInfos[position])
        holder.habitInfo = habitsInfos[position]
        holder.position = position

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = habitsInfos.size

    fun addHabitInfo(habitInfo: HabitInfo) {
        habitsInfos.add(habitInfo)
    }

    fun changeHabitInfo(habitInfoPosition: Int, habitInfo: HabitInfo) {
        habitsInfos[habitInfoPosition] = habitInfo
    }
}