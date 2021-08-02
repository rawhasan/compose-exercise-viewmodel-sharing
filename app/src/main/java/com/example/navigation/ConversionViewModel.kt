package com.example.navigation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.roundToLong

class ConversionViewModel : ViewModel() {
    private var _fahrenheit = 0
    val fahrenheit
        get() = _fahrenheit

    private var _celsius = 0.0
    val celsius
        get() = _celsius

    private var _celsiusText = ""
    val celsiusText
        get() = _celsiusText

    fun onCalculate(input: Int) {
        if (input in 0..160) {
            _fahrenheit = input
            calculateCelsius(_fahrenheit!!)
        } else
            _fahrenheit = 0
    }

    private fun calculateCelsius(fh: Int) {
        var cs: Double = (fh.toDouble() - 32) * 5 / 9
        _celsius = String.format("%.1f", cs).toDouble()

        val csText = String.format("%.1f", cs)
        val lastChar = csText[csText.length - 1]

        _celsiusText = if (lastChar.digitToInt() == 0) {
            cs.toInt().toString()
        } else {
            String.format("%.1f", cs)
        }
    }
}