package com.example.habitstracker.fragments

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.habitstracker.HabitType
import com.example.habitstracker.R
import com.example.habitstracker.adapters.HabitsViewPagerAdapter
import com.example.habitstracker.viewModels.HabitsListViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    private lateinit var viewAdapter: HabitsViewPagerAdapter
    private var editingFragment: HabitEditingFragment? = null
    private val habitsTypesList = arrayListOf("Позитивные", "Негативные")
    private val habitsListViewModel: HabitsListViewModel by activityViewModels()
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
        val bottomSheet = bottom_sheet
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        fab.setOnClickListener(this::onFabClick)
        habitsListViewModel.habitInfos.observe(viewLifecycleOwner, Observer { habitInfos ->
            //TODO не передавать списки
            viewAdapter = HabitsViewPagerAdapter(

                ArrayList(habitInfos.filter { habitInfo -> habitInfo.type == HabitType.Positive.type }),
                ArrayList(habitInfos.filter { habitInfo -> habitInfo.type == HabitType.Negative.type }),
                this
            )
            val viewPager = mainPager
            viewPager.adapter = viewAdapter
            val tabLayout = tabs
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = habitsTypesList[position]
            }.attach()
        })

        searchEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                habitsListViewModel.searchByName(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        sortNameTopButton.setOnClickListener { habitsListViewModel.sortByName(true) }
        sortNameBottomButton.setOnClickListener { habitsListViewModel.sortByName(false) }

    }

    private fun onFabClick(view: View) {
        editingFragment = HabitEditingFragment.newInstance()
        requireActivity().findNavController(R.id.nav_host_fragment)
            .navigate(R.id.action_mainFragment_to_habitEditingFragment)

    }

}