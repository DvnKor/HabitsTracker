package com.example.habitstracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
    private var habitsInfos: ArrayList<HabitInfo> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener(this::onFabClick)
        if (savedInstanceState != null)
            habitsInfos =
                savedInstanceState.getParcelableArrayList<HabitInfo>("habitsInfos") as ArrayList<HabitInfo>
        viewManager = LinearLayoutManager(this)
        viewAdapter = HabitsAdapter(habitsInfos)
        recyclerView = habitsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("habitsInfos", habitsInfos)
    }

    private fun onFabClick(view: View) {
        val intent = Intent(this, HabitEditingActivity::class.java)
        startActivityForResult(intent, changeHabitRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != changeHabitRequestCode) return
        if (resultCode == Activity.RESULT_OK && data != null) {
            val position = data.extras?.getInt("habitInfoPosition", -1) ?: -1
            val habitInfo = data.extras?.getParcelable<HabitInfo>("habitInfo") as HabitInfo
            if (position != -1)
                viewAdapter.changeHabitInfo(position, habitInfo)
            else
                viewAdapter.addHabitInfo(habitInfo)
            viewAdapter.notifyDataSetChanged()
        }
    }

}
