package com.example.habitstracker

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.core.view.children
import androidx.core.view.doOnLayout
import kotlinx.android.synthetic.main.activity_habit_editing.*
import kotlin.math.round


class HabitEditingActivity : AppCompatActivity() {
    private var habitInfo: HabitInfo? = null
    private var habitInfoPosition: Int? = null
    private val colorPickerSquaresNumber = 16

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_editing)
        setListeners()
        habitInfo = intent?.extras?.getParcelable("habitInfo") as HabitInfo?
        habitInfoPosition = intent.extras?.getInt("habitInfoPosition", -1) ?: -1
        chosenColorDisplay.setBackgroundColor(habitInfo?.color ?: Color.WHITE)
        createColorButtons()
        colorPickerLayout.doOnLayout(this::onButtonsLayout)

        if (habitInfo != null) {
            updateViews(habitInfo)
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun createColorButtons() {
        val colors = getGradientColors(60F, 0.5F, 0.5F)
        val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors)
        colorPickerLayout.background = gradientDrawable
        for (buttonNumber in 0 until colorPickerSquaresNumber) {
            val button = Button(this)
            button.setBackgroundColor(colors[0])
            val params = LinearLayout.LayoutParams(200, 200)
            params.setMargins(50, 50, 50, 50)
            button.layoutParams = params
            button.setOnClickListener(this::onColorSquareClick)
            colorPickerLayout.addView(button)
        }
    }

    private fun onColorSquareClick(view: View) {
        val color = (view.background as ColorDrawable).color
        chosenColorDisplay.setBackgroundColor(color)
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        val rgb = getRGBFromHex(color)
        rgbColorView.text =
            this.resources.getString(R.string.rgbColorString, rgb[0], rgb[1], rgb[2])
        hsvColorView.text =
            this.resources.getString(R.string.hsvColorString, hsv[0], hsv[1], hsv[2])
    }

    private fun getGradientColors(hueStep: Float, saturation: Float, value: Float): IntArray {
        var currentHue = 0.0F
        val result = ArrayList<Int>()
        while (currentHue < 360) {
            result.add(Color.HSVToColor(floatArrayOf(currentHue, saturation, value)))
            currentHue += hueStep
        }
        return result.toIntArray()
    }

    private fun onButtonsLayout(view: View) {
        val layout = view as LinearLayout
        val drawable = layout.background as GradientDrawable
        val mutableBitmap =
            Bitmap.createBitmap(layout.width, layout.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(mutableBitmap)
        drawable.setBounds(0, 0, layout.width, layout.height)
        drawable.draw(canvas)
        for (btn in layout.children) {
            val pixelX = round(btn.x + btn.width / 2).toInt()
            val pixelY = round(btn.y + btn.height / 2).toInt()
            val pixel = mutableBitmap.getPixel(pixelX, pixelY)
            btn.setBackgroundColor(Color.rgb(pixel.red, pixel.green, pixel.blue))
        }
    }

    private fun getRGBFromHex(hex: Int): IntArray {
        val r = hex and 0xFF0000 shr 16
        val g = hex and 0xFF00 shr 8
        val b = hex and 0xFF
        return intArrayOf(r, g, b)
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
            color = (chosenColorDisplay.background as ColorDrawable).color
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
