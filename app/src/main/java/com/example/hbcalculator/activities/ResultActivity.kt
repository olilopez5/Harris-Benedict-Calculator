package com.example.hbcalculator.activities
import com.example.hbcalculator.R
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ResultActivity : AppCompatActivity() {
    private lateinit var lightSurplus: TextView
    private lateinit var moderateSurplus: TextView
    private lateinit var aggressiveSurplus: TextView
    private lateinit var veryAggressiveSurplus: TextView

    private lateinit var lightDeficit: TextView
    private lateinit var moderateDeficit: TextView
    private lateinit var aggressiveDeficit: TextView
    private lateinit var veryAggressiveDeficit: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Inicializar los TextViews
        lightSurplus = findViewById(R.id.lightSurplus)
        moderateSurplus = findViewById(R.id.moderateSurplus)
        aggressiveSurplus = findViewById(R.id.aggressiveSurplus)
        veryAggressiveSurplus = findViewById(R.id.veryAggressiveSurplus)


        lightDeficit = findViewById(R.id.lightDeficit)
        moderateDeficit = findViewById(R.id.moderateDeficit)
        aggressiveDeficit = findViewById(R.id.aggressiveDeficit)
        veryAggressiveDeficit = findViewById(R.id.deficitVeryAggressive)

         //Obtener el valor de las calorías desde el Intent
        val balanceResult = intent.getDoubleExtra("caloriesResult", 0.0)

        // Calcular y mostrar los resultados
        lightSurplus.setText((balanceResult + 200).toString() + " kcal/día")
        moderateSurplus.setText((balanceResult + 400).toString() + " kcal/día")
        aggressiveSurplus.setText((balanceResult + 600).toString() + " kcal/día")
        veryAggressiveSurplus.setText((balanceResult + 800).toString() + " kcal/día")

        lightDeficit.setText((balanceResult - 200).toString() + " kcal/día")
        moderateDeficit.setText((balanceResult - 400).toString() + " kcal/día")
        aggressiveDeficit.setText((balanceResult - 600).toString() + " kcal/día")
        veryAggressiveDeficit.setText((balanceResult - 800).toString() + " kcal/día")
    }
}
