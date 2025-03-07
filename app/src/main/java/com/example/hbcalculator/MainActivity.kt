package com.example.hbcalculator

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Locale
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    companion object {
        val activityLevel = arrayOf(
            "Sedentario: poco o nada de ejercicio al día",
            "Actividad ligera: ejercicio 1-3 días a la semana",
            "Actividad moderada: ejercicio 3-5 días a la semana",
            "Actividad intensa: ejercicio 6-7 días a la semana",
            "Actividad muy intensa: trabajo físico y ejercicio diario"
        )
        val objectiveList = arrayOf(
            "Aumentar masa muscular",
            "Bajar grasa corporal",
            "Mantener el peso actual"
        )
    }


    lateinit var ageTextField: TextInputEditText

    lateinit var heightTextField: TextInputLayout

    lateinit var weightTextField: TextInputLayout


    lateinit var calculateButton: Button
    //lateinit var resultTMB: TextView
    //lateinit var descriptionTextView: TextView

    lateinit var activityLevel: TextInputLayout
    lateinit var objectiveSpinner: Spinner

    var height = 170.0f
    var weight = 75.0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //R. Res Vistas
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nameTextField: AutoCompleteTextView = arrayOf()

        val activities = resources.getStringArray(R.array.arrayActivities)

        ArrayAdapter(this, android.R.layout.simple_list_item_1, activities).also { adapter ->
            nameTextField.setAdapter(adapter)
        }

        initViews()


        //height = value
        heightTextField.editText?.setText("${height.toInt()}")
        weightTextField.editText?.setText("${weight.toInt()}")
    }



    fun calculateBMI() {
        weight = weightTextField.editText!!.text.toString().toFloat()
        height = heightTextField.editText!!.text.toString().toFloat()

    //spiner permite selectedItemPosition ,
//        var selectedActivity = activitySpinner.selectedItemPosition
//        println("Nivel de actividad: ${activityLevel[selectedActivity]}")

        var selectedObjective = objectiveSpinner.selectedItemPosition
        println("Objetivo: ${objectiveList[selectedObjective]}")

        val result = weight / (height / 100).pow(2)

        //formateo de resultado para adaptarlo al idioma del telefono y dos decimales

        //resultBMI.text = String.format(Locale.getDefault(), "%.2f", result)

        var colorId = 0
        var textId = 0

        when (result) {
            in 0f..18.5f -> {
                //"Bajo peso"
                colorId = R.color.bmi_underweight
                textId = R.string.bmi_underweight
            }

            in 18.5f..24.9f -> {
                //"Peso normal"
                colorId = R.color.bmi_normal
                textId = R.string.bmi_normal
            }

            in 25.0f..29.9f -> {
                //"Sobrepeso"
                colorId = R.color.bmi_overweight
                textId = R.string.bmi_overweight
            }

            in 30.0f..34.9f -> {
                //"Obesidad (Grado 1)"
                colorId = R.color.bmi_obesity1
                textId = R.string.bmi_obesity1
            }

            in 35.0f..39.9f -> {
                //"Obesidad (Grado 2)"
                colorId = R.color.bmi_obesity2
                textId = R.string.bmi_obesity2
            }

            else -> {
                colorId = R.color.bmi_obesity3
                textId = R.string.bmi_obesity3
                showObesityDialog()
            }

        }
        //resultBMI.setTextColor(getColor(colorId))
        //descriptionTextView.setTextColor(getColor(colorId))
        //descriptionTextView.text = getString(textId)
    }

    fun initViews() {
        //heightTextView = findViewById(R.id.heightTextView)
        heightTextField = findViewById(R.id.heightTextField)

        weightTextField = findViewById(R.id.weightTextField)
        //minusButton = findViewById(R.id.minusButton)
        //addButton = findViewById(R.id.addButton)


        calculateButton = findViewById(R.id.calculateButton)
        //resultBMI = findViewById(R.id.resultBMI)
        //descriptionTextView = findViewById(R.id.descriptionTextView)

        //activitySpinner = findViewById(R.id.activitySpinner)
        objectiveSpinner = findViewById(R.id.objectiveSpinner)


        // Create the instance of ArrayAdapter
        val objectiveSpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, objectiveList)
        // set simple layout resource file for each item of spinner
        objectiveSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        objectiveSpinner.adapter = objectiveSpinnerAdapter

        calculateButton.setOnClickListener {
            calculateBMI()
        }
    }

    fun showObesityDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.obesity3_alert_title)
            .setMessage(R.string.obesity3_alert_message)
            .setPositiveButton(R.string.obesity3_alert_positive_button, { dialog, which ->
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://hospitalcruzrojacordoba.es/obesidad-nutricion-cordoba/obesidad-morbida-tratamiento-para-adelgazar/")
                )
                startActivity(browserIntent)
            })
            .setNegativeButton(android.R.string.cancel, null)
            .show()

    }
}