package com.example.hbcalculator

class HBFormula {

        fun calculateBMR(weight: Float, height: Float, age: Int, isMale: Boolean): Float {
            return if (isMale) {
                // Fórmula de Harris-Benedict para hombres
                66.5f + (13.75f * weight) + (5.003f * height) - (6.75f * age)
            } else {
                // Fórmula de Harris-Benedict para mujeres
                655f + (9.563f * weight) + (1.850f * height) - (4.676f * age)
            }
        }

        fun calculateTDEE(bmr: Float, activityLevel: Int): Float {
            val activityMultiplier = when (activityLevel) {
                0 -> 1.2f // Sedentario
                1 -> 1.375f // Ligera actividad
                2 -> 1.55f // Actividad moderada
                3 -> 1.725f // Alta actividad
                4 -> 1.9f // Actividad extrema
                else -> 1.2f // Default
            }
            return bmr * activityMultiplier
        }
    }

