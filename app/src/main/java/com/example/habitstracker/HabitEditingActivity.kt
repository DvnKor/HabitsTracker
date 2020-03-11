package com.example.habitstracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class HabitEditingActivity : AppCompatActivity() {
    private var name: String = ""
    private var description: String = ""
    private var type: String = ""
    private var numberOfRepeats: Int = 0
    private var numberOfDays: Int = 0
    private var color: Int = 0
    private var priority: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name = intent?.extras?.getString("name", "") ?: ""
        description = intent?.extras?.getString("description", "") ?: ""
        type = intent?.extras?.getString("type", "") ?: ""
        numberOfRepeats = intent?.extras?.getInt("numberOfRepeats", 0) ?: 0
        numberOfDays = intent?.extras?.getInt("numberOfDays", 0) ?: 0
        color = intent?.extras?.getInt("color", 0) ?: 0
        priority = intent?.extras?.getInt("priority", 0) ?: 0
        setContentView(R.layout.activity_habit_editing)
    }
}
