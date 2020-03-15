package com.example.habitstracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import kotlinx.android.synthetic.main.activity_habit_editing.*

class HabitEditingActivity : AppCompatActivity() {
    private var habitInfo: HabitInfo? = null
    private var habitInfoPosition: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_editing)
        setListeners()
        habitInfo = intent?.extras?.getParcelable("habitInfo") as HabitInfo?
        habitInfoPosition = intent.extras?.getInt("habitInfoPosition", -1) ?: -1
        if (habitInfo != null) {
            updateViews(habitInfo)
        }
    }

    private fun setListeners() {
        saveButton.setOnClickListener(this::onSaveClick)
        cancelButton.setOnClickListener(this::onCancelClick)
        editDescription.setOnTouchListener { view, event ->
            view.parent.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> view.parent.parent
                    .requestDisallowInterceptTouchEvent(false)
            }
            false
        }
    }

    private fun updateViews(habitInfo: HabitInfo?) {
        editName.setText(habitInfo?.name ?: "")
        editDescription.setText(habitInfo?.description ?: "")
        typeRadioGroup.check(getCheckedButtonId(typeRadioGroup, habitInfo?.type))
        prioritySpinner.setSelection(getSelectedItemPosition(prioritySpinner, habitInfo?.priority))
        editNumberOfDays.setText(habitInfo?.numberOfDays.toString())
        editNumberOfRepeats.setText(habitInfo?.numberOfRepeats.toString())
    }

    private fun getSelectedItemPosition(spinner: Spinner, text: String?): Int {
        val elementsCount = spinner.count
        for (elementPos in 0 until elementsCount) {
            if (spinner.getItemAtPosition(elementPos).toString() == text)
                return elementPos
        }
        return 0
    }

    private fun getCheckedButtonId(radioGroup: RadioGroup, text: String?): Int {
        for (child in radioGroup.children) {
            if ((child as RadioButton).text == text)
                return child.id
        }
        return 0
    }

    private fun saveUserInput() {
        habitInfo = HabitInfo(
            name = editName.text.toString(),
            description = editDescription.text.toString(),
            type = findViewById<RadioButton>(typeRadioGroup.checkedRadioButtonId).text.toString(),
            numberOfRepeats = editNumberOfRepeats.text.toString().toIntOrNull() ?: 0,
            numberOfDays = editNumberOfDays.text.toString().toIntOrNull() ?: 0,
            priority = prioritySpinner.selectedItem.toString(),
            color = 0
        )

    }

    private fun getUpdatedIntent(): Intent {
        val updatedIntent = Intent()
        if (habitInfo == null)
            return updatedIntent
        return updatedIntent.putExtra("habitInfo", habitInfo)
            .putExtra("habitInfoPosition", habitInfoPosition)
    }

    private fun onSaveClick(view: View) {
        saveUserInput()
        setResult(Activity.RESULT_OK, getUpdatedIntent())
        finish()
    }

    private fun onCancelClick(view: View) {
        setResult(Activity.RESULT_OK, getUpdatedIntent())
        finish()
    }
}
