package com.example.hbcalculator.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hbcalculator.HBFormula
import com.example.hbcalculator.R
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    lateinit var ageTextField: TextInputEditText
    lateinit var maleButton: Button
    lateinit var femaleButton: Button
    lateinit var heightTextField: TextInputEditText
    lateinit var weightTextField: TextInputEditText

    lateinit var activityLevel: AutoCompleteTextView
    lateinit var objectiveText: AutoCompleteTextView


    lateinit var calculateButton: Button
    private var balanceResult: TextView? = null

    private var isMale: Boolean = true

    var activity = -1
    var objective = -1


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

        initViews()

        // Configura los listeners para los botones de sexo
        maleButton.setOnClickListener {
            isMale = true
        }

        femaleButton.setOnClickListener {
            isMale = false
        }

        calculateButton.setOnClickListener {
            calculateCalories()
        }
    }

        private fun calculateCalories() {

            val weight = weightTextField.text.toString().toFloat()
            val height = heightTextField.text.toString().toFloat()
            val age = ageTextField.text.toString().toInt()

            val isMale = true // Asume hombre o mujer, lo puedes hacer con un RadioButton

            // Usar HBFormula para obtener el BMR
            val calculator = HBFormula()
            val bmr = calculator.calculateBMR(weight, height, age, isMale)



            // Verificar el nivel de actividad
            val activityLevelText = activityLevel.text.toString()

            // Si el usuario selecciona "Mantener peso", mostrar directamente el resultado en la pantalla principal
            if (activityLevelText == "Mantener peso") {
                balanceResult?.text  = getString(R.string.balance) + " $bmr kcal/day"
            } else {
                // Si es otra opción, redirigir a la actividad de resultados
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("BMR", bmr)
                intent.putExtra("ActivityLevel", activityLevelText)
                startActivity(intent)
            }
        }

    private fun initViews() {
        //java.lang.ClassCastException
        ageTextField = findViewById(R.id.ageTextField)
        maleButton = findViewById(R.id.maleButton)
        femaleButton = findViewById(R.id.femaleButton)
        heightTextField = findViewById(R.id.heightTextField)
        weightTextField = findViewById(R.id.weightTextField)
        activityLevel = findViewById(R.id.acLevelAutoComplete)
        objectiveText = findViewById(R.id.objectiveAutoComplete)
        calculateButton = findViewById(R.id.calculateButton)
        balanceResult = findViewById(R.id.balanceResultTextField)

        // Crear el adaptador para el nivel de actividad
        val activities = resources.getStringArray(R.array.arrayActivities)
        val activityAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, activities)
        activityLevel.setAdapter(activityAdapter)

        // Crear el adaptador para el objetivo
        val objectiveList = resources.getStringArray(R.array.arrayObjective)
        val objectiveAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, objectiveList)
        objectiveText.setAdapter(objectiveAdapter)

    }

}

        //acLevelAutoComplete.setOnItemClickListener { parent, view, position, id ->
        //activity = position
       //}
// Create the instance of ArrayAdapter

        //val objectiveSpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, objectiveList)
// set simple layout resource file for each item of spinner
        //objectiveSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //objectiveSpinner.adapter = objectiveSpinnerAdapter






