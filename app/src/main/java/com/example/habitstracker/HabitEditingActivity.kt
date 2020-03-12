package com.example.habitstracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_habit_editing.*

class HabitEditingActivity : AppCompatActivity() {
    private var name: String = ""
    private var description: String = ""
    private var type: String = ""
    private var numberOfRepeats: Int = 0
    private var numberOfDays: Int = 0
    private var color: Int = 0
    private var priority: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name = intent?.extras?.getString("name", "") ?: ""
        description = intent?.extras?.getString("description", "") ?: ""
        type = intent?.extras?.getString("type", "") ?: ""
        numberOfRepeats = intent?.extras?.getInt("numberOfRepeats", 0) ?: 0
        numberOfDays = intent?.extras?.getInt("numberOfDays", 0) ?: 0
        color = intent?.extras?.getInt("color", 0) ?: 0
        priority = intent?.extras?.getString("priority", "") ?: ""
        setContentView(R.layout.activity_habit_editing)
    }

    private fun saveUserInput() {
        name = editName.text.toString()
        description = editDescription.text.toString()
        type = findViewById<RadioButton>(typeRadioGroup.checkedRadioButtonId).text.toString()
        numberOfRepeats = editNumberOfRepeats.text.toString().toInt()
        numberOfDays = editNumberOfDays.text.toString().toInt()
        priority = prioritySpinner.selectedItem.toString()
        color = 0

    }

    private fun getUpdatedIntent(): Intent {
        val updatedIntent = Intent()
        val bundle = Bundle()
        updatedIntent.putExtra("name", name)
        updatedIntent.putExtra("description", description)
        updatedIntent.putExtra("type", type)
        updatedIntent.putExtra("numberOfRepeats", numberOfRepeats)
        updatedIntent.putExtra("numberOfDays", numberOfDays)
        updatedIntent.putExtra("priority", priority)
        updatedIntent.putExtra("color", color)
        return updatedIntent
    }

    fun onSaveClick(view: View) {
        saveUserInput()
        setResult(Activity.RESULT_OK, getUpdatedIntent())
        finish()
    }

    fun onCancelClick(view: View) {
        setResult(Activity.RESULT_OK, getUpdatedIntent())
        finish()
    }
}
