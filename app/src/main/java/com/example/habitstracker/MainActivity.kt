package com.example.habitstracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fab = findViewById<View>(R.id.fab)
        fab.setOnClickListener(this::onFabClick)
    }

    private fun onFabClick(view: View) {
        val intent = Intent(this, HabitEditingActivity::class.java)
        val result = startActivityForResult(intent, 0)
        Log.i("clicked", "clicked")
    }
}
