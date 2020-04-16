package com.example.habitstracker.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habitstracker.HabitInfo
import com.example.habitstracker.HabitType
import com.example.habitstracker.IHabitChangedCallback
import com.example.habitstracker.R
import com.example.habitstracker.adapters.HabitsRecyclerViewAdapter
import com.example.habitstracker.viewModels.HabitsListViewModel
import kotlinx.android.synthetic.main.fragment_habit_list.*

class HabitListFragment : Fragment() {

    companion object {
        private const val habitTypeArgName = "habitInfoType"
        fun newInstance(
            habitInfoType: String
        ): HabitListFragment {
            val fragment = HabitListFragment()
            val bundle = Bundle()
            bundle.putString(habitTypeArgName, habitInfoType)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val habitsListViewModel: HabitsListViewModel by activityViewModels()
    private lateinit var habitType: String
    private var habitInfos: List<HabitInfo> = listOf()
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
            habitType = it.getString(habitTypeArgName) ?: HabitType.Positive.type
        }
        val viewManager = LinearLayoutManager(context)
        viewAdapter =
            HabitsRecyclerViewAdapter(
                habitInfos,
                requireActivity().findNavController(R.id.nav_host_fragment)
            )
        habitsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        habitsListViewModel.habitInfos.observe(
            viewLifecycleOwner,
            Observer { habitInfos ->
                this.habitInfos = habitInfos.filter { habitInfo -> habitInfo.type == habitType }
                viewAdapter.setHabitInfos(this.habitInfos)
                viewAdapter.notifyDataSetChanged()
            })
    }


}