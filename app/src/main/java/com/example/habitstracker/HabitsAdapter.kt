package com.example.habitstracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.habit_info_view.view.*

class HabitsAdapter(private val habitsInfos: MutableList<HabitInfo>) :
    RecyclerView.Adapter<HabitsAdapter.HabitViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class HabitViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.name
        val descriptionView: TextView = view.description
        val priorityView: TextView = view.priority
        val typeView: TextView = view.type
        val periodView: TextView = view.period
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HabitsAdapter.HabitViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.habit_info_view, parent, false)
        // set the view's size, margins, paddings and layout parameters
        // ...
        return HabitViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.nameView.text = habitsInfos[position].name
        holder.descriptionView.text = habitsInfos[position].description
        holder.typeView.text = habitsInfos[position].type
        holder.priorityView.text = habitsInfos[position].priority
        holder.periodView.text =
            "${habitsInfos[position].numberOfRepeats} раз в ${habitsInfos[position].numberOfDays} дней"

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = habitsInfos.size

    fun addHabitInfo(habitInfo: HabitInfo) {
        habitsInfos.add(habitInfo)
    }
}