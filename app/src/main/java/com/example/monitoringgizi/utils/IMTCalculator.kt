package com.example.monitoringgizi.utils

object IMTCalculator {
    fun calculateIMT(beratBadan: Float, tinggiBadan: Float): Float {
        if (tinggiBadan <= 0f || beratBadan <= 0f) return 0f
        val tinggiMeter = tinggiBadan / 100
        return beratBadan / (tinggiMeter * tinggiMeter)
    }

    fun getKategoriIMT(imt: Float): String {
        return when {
            imt < 18.5f -> "Kurus"
            imt < 24.9f -> "Normal"
            imt < 29.9f -> "Gemuk"
            else -> "Obesitas"
        }
    }
}