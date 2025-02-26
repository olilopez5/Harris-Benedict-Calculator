package com.example.hbcalculator

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.slider.Slider
import java.util.Locale
import kotlin.math.pow

class MainActivity : AppCompatActivity() {


    lateinit var heightSlider: Slider
    lateinit var heightTextView: EditText

    lateinit var weightTextView: TextView
    lateinit var addButton: Button
    lateinit var minusButton: Button

    lateinit var calculateButton: Button
    lateinit var resultBMI: TextView
    lateinit var descriptionTextView : TextView

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

        initViews()


        heightSlider.addOnChangeListener { slider, value, fromUser ->
            height = value
            heightTextView.setText("${height.toInt()}")
        }

        heightTextView.addTextChangedListener {
            val text = heightTextView.text.toString()
            if (text.isNotEmpty()) {
                if (text.toFloat() >= heightSlider.valueFrom && text.toFloat() <= heightSlider.valueTo) {
                    heightSlider.value = text.toFloat()
                }
            }
        }

        addButton.setOnClickListener {
            weight++
            if (weight > 300){
                weight = 300f
            }
            weightTextView.text = "${weight.toInt()}"
        }

        minusButton.setOnClickListener {
            weight--
            if(weight < 1){
                weight = 1f
            }
            weightTextView.text = "${weight.toInt()}"
        }

        calculateButton.setOnClickListener {
            val result = weight / (height / 100).pow(2)

            //formateo de resultado para adaptarlo al idioma del telefono y dos decimales

            resultBMI.text = String.format(Locale.getDefault(), "%.2f", result)

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
            resultBMI.setTextColor(getColor(colorId))
            descriptionTextView.setTextColor(getColor(colorId))
            descriptionTextView.text = getString(textId)
        }
    }
    fun initViews(){
        heightTextView = findViewById(R.id.heightTextView)
        heightSlider = findViewById(R.id.heightSlider)

        weightTextView = findViewById(R.id.weightTextView)
        minusButton = findViewById(R.id.minusButton)
        addButton = findViewById(R.id.addButton)


        calculateButton = findViewById(R.id.calculateButton)
        resultBMI = findViewById(R.id.resultBMI)
        descriptionTextView = findViewById(R.id.descriptionTextView)
    }

    fun showObesityDialog(){
        AlertDialog.Builder(this)
            .setTitle(R.string.obesity3_alert_title)
            .setMessage(R.string.obesity3_alert_message)
            .setPositiveButton(R.string.obesity3_alert_positive_button, { dialog , which ->
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://hospitalcruzrojacordoba.es/obesidad-nutricion-cordoba/obesidad-morbida-tratamiento-para-adelgazar/"))
                startActivity(browserIntent)
            })
            .setNegativeButton(android.R.string.cancel,null)
            .show()

    }
}