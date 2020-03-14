package com.example.habitstracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_habit_editing.*

class HabitEditingActivity : AppCompatActivity() {
    private var habitInfo: HabitInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_editing)
        habitInfo = intent?.extras?.getSerializable("habitInfo") as HabitInfo?
        if (habitInfo != null) {
            val ename = findViewById<EditText>(R.id.editName)
            val a = editName
            editName.setText(habitInfo?.name ?: "")
            editDescription.setText(habitInfo?.description ?: "")
            //type
            //p
            editNumberOfDays.setText(habitInfo?.numberOfDays.toString())
            editNumberOfRepeats.setText(habitInfo?.numberOfRepeats.toString())
        }
    }

    private fun saveUserInput() {
        habitInfo = HabitInfo(
            name = editName.text.toString(),
            description = editDescription.text.toString(),
            type = findViewById<RadioButton>(typeRadioGroup.checkedRadioButtonId).text.toString(),
            numberOfRepeats = editNumberOfRepeats.text.toString().toInt(),
            numberOfDays = editNumberOfDays.text.toString().toInt(),
            priority = prioritySpinner.selectedItem.toString(),
            color = 0
        )

    }

    private fun getUpdatedIntent(): Intent {
        val updatedIntent = Intent()
        val bundle = Bundle()
        updatedIntent.putExtra("habitInfo", habitInfo)
        return updatedIntent
    }

    //TODO: private???
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
