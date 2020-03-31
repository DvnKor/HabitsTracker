package com.example.habitstracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), IHabitChangedCallback {
    val changeHabitRequestCode = 1
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: HabitsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var habitsInfos: ArrayList<HabitInfo> = arrayListOf()
    private var editingFragment: HabitEditingFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener(this::onFabClick)
        if (savedInstanceState != null) {
            habitsInfos =
                savedInstanceState.getParcelableArrayList<HabitInfo>("habitsInfos") as ArrayList<HabitInfo>
        } else {
            //val fragment = HabitEditingFragment.newInstance()
        }
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
        // intent = Intent(this, HabitEditingActivity::class.java)
        editingFragment = HabitEditingFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.mainLayout, editingFragment as HabitEditingFragment).commit()
        //startActivityForResult(intent, changeHabitRequestCode)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode != changeHabitRequestCode) return
//        if (resultCode == Activity.RESULT_OK && data != null) {
//            val position = data.extras?.getInt("habitInfoPosition", -1) ?: -1
//            val habitInfo = data.extras?.getParcelable<HabitInfo>("habitInfo")
//            if (habitInfo != null) {
//                if (position != -1)
//                    viewAdapter.changeHabitInfo(position, habitInfo)
//                else
//                    viewAdapter.addHabitInfo(habitInfo)
//            }
//            viewAdapter.notifyDataSetChanged()
//        }
//    }

    override fun onHabitChanged(position: Int?, habitInfo: HabitInfo?) {
        if (habitInfo == null)
            return
        if (position != null) {
            viewAdapter.changeHabitInfo(position, habitInfo)
        } else {
            viewAdapter.addHabitInfo(habitInfo)
        }
        viewAdapter.notifyDataSetChanged()
        supportFragmentManager.beginTransaction().remove(editingFragment as HabitEditingFragment)

    }

}
