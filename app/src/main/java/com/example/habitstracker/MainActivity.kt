package com.example.habitstracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val changeHabitRequestCode = 1

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: HabitsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val habitsInfos: MutableList<HabitInfo> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener(this::onFabClick)
        viewManager = LinearLayoutManager(this)
        viewAdapter = HabitsAdapter(habitsInfos)
        recyclerView = habitsRecyclerView.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun onFabClick(view: View) {
        val intent = Intent(this, HabitEditingActivity::class.java)
        startActivityForResult(intent, changeHabitRequestCode)
        Log.i("clicked", "clicked")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != changeHabitRequestCode) return
        if (resultCode == Activity.RESULT_OK) {
            viewAdapter.addHabitInfo(data?.extras?.getSerializable("habitInfo") as HabitInfo)
            viewAdapter.notifyDataSetChanged()
        }
    }

}
