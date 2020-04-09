package com.example.habitstracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.habitstracker.repository.InMemoryHabitsRepository
import com.example.habitstracker.viewModels.HabitEditingViewModel
import com.example.habitstracker.viewModels.HabitsListViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), IHabitChangedCallback {
    private val navControllerStateArgName = "navControllerState"
    private lateinit var navController: NavController
    private lateinit var habitsListViewModel: HabitsListViewModel
    private lateinit var habitEditingViewModel: HabitEditingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = InMemoryHabitsRepository()
        habitsListViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HabitsListViewModel(repository) as T
            }
        }).get(HabitsListViewModel::class.java)

        habitEditingViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HabitEditingViewModel(repository, habitsListViewModel) as T
            }
        }).get(HabitEditingViewModel::class.java)

        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_info -> {
                    onAppInfoMenuClick()
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_habits -> {
                    onHabitsListMenuClick()
                    drawerLayout.closeDrawers()
                    true
                }

                else -> false
            }
        }
        if (savedInstanceState != null) {
            navController.restoreState(savedInstanceState.getBundle(navControllerStateArgName))
        }
    }


    private fun onHabitsListMenuClick() {
        navController.navigate(R.id.mainFragment)
    }

    private fun onAppInfoMenuClick() {
        navController.navigate(R.id.appInfoFragment)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBundle(navControllerStateArgName, navController.saveState())
    }

    override fun onHabitChanged() {
        navController.navigate(R.id.action_habitEditingFragment_to_mainFragment)
    }

}
