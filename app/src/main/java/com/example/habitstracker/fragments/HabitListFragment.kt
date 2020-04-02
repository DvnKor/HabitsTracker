package com.example.habitstracker.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habitstracker.HabitInfo
import com.example.habitstracker.adapters.HabitsRecyclerViewAdapter
import com.example.habitstracker.IHabitChangedCallback
import com.example.habitstracker.R
import kotlinx.android.synthetic.main.fragment_habit_list.*

class HabitListFragment : Fragment() {
    companion object {
        private const val habitInfosArgName = "habitInfos"
        fun newInstance(
            habitInfos: ArrayList<HabitInfo> = arrayListOf()
        ): HabitListFragment {
            val fragment =
                HabitListFragment()
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
        viewAdapter =
            HabitsRecyclerViewAdapter(
                habitInfos,
                activity!!.findNavController(R.id.nav_host_fragment)
            )
        habitsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }


}