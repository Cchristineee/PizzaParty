package com.zybooks.pizzaparty

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.String

class MainActivity : AppCompatActivity() {

    private lateinit var numAttendEditText: EditText
    private lateinit var numPizzasTextView: TextView
    private lateinit var howHungryRadioGroup: RadioGroup
    private lateinit var historyText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        numAttendEditText = findViewById(R.id.num_attend_edit_text)
        numPizzasTextView = findViewById(R.id.num_pizzas_text_view)
        howHungryRadioGroup = findViewById(R.id.hungry_radio_group)
        historyText = findViewById(R.id.history)

        historyText.movementMethod = ScrollingMovementMethod.getInstance()

        if (savedInstanceState != null){
            historyText.setText(savedInstanceState.getString("textView"))
            numPizzasTextView.setText(savedInstanceState.getString("result"))
        }
    }

    fun calculateClick(view: View) {

        // Get the text that was typed into the EditText
        val numAttendStr = numAttendEditText.text.toString()

        // Convert the text into an integer
        val numAttend = numAttendStr.toIntOrNull()?:0

        // Determine how many slices on average each person will eat
        val hungerLevel = when (howHungryRadioGroup.checkedRadioButtonId) {
            R.id.light_radio_button -> PizzaCalculator.HungerLevel.LIGHT
            R.id.medium_radio_button -> PizzaCalculator.HungerLevel.MEDIUM
            else -> PizzaCalculator.HungerLevel.RAVENOUS
        }

        // Calculate and show the number of pizzas needed
        val calc = PizzaCalculator(numAttend, hungerLevel)
        val numPizza = calc.totalPizzas

        // Place total pizzas into the String resource and display
        val totalText = getString(R.string.total_pizzas, numPizza)
        numPizzasTextView.setText(totalText)
        historyText.append(totalText+"\n")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("textView", historyText.text.toString())
        outState.putString("result", numPizzasTextView.text.toString())
        super.onSaveInstanceState(outState)
    }
}