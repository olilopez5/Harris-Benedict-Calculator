package com.example.hbcalculator

class HBFormula {

    // calcular el metabolismo basal

    fun calculateBMR(weight: Float, height: Float, age: Int, isMale: Boolean): Double {
        val bmr = if (isMale) {
            // Fórmula de Harris-Benedict para hombres
            66 + (13.75 * weight) + (5 * height) - (6.75 * age)
        } else {
            // Fórmula de Harris-Benedict para mujeres
            655 + (9.56 * weight) + (1.85 * height) - (4.68 * age)
        }

        return bmr
    }


    // calorías según el nivel de actividad
    fun calculateCalories(bmr: Int, activityLevel: String?): Double {
        var factor = 1.0

        factor = when (activityLevel) {
            "light" -> 1.2
            "moderate" -> 1.375
            "intense" -> 1.55
            "very_intense" -> 1.725
            else -> 1.0 // Para balance, sin cambio
        }

        return bmr * factor
    }
}
