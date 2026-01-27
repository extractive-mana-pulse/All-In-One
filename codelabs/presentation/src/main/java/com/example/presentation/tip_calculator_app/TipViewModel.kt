package com.example.presentation.tip_calculator_app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import kotlin.math.ceil

class TipViewModel : ViewModel() {

    var amountInput by mutableStateOf("")
        private set

    var tipInput by mutableStateOf("")
        private set

    var roundUp by mutableStateOf(false)
        private set

    fun onAmountChange(value: String) {
        amountInput = value
    }

    fun onTipChange(value: String) {
        tipInput = value
    }

    fun onRoundUpChange(value: Boolean) {
        roundUp = value
    }

    fun calculateTip(): String {
        val amount = amountInput.toDoubleOrNull() ?: 0.0
        val tipPercent = tipInput.toDoubleOrNull() ?: 0.0

        var tip = tipPercent / 100 * amount
        if (roundUp) {
            tip = ceil(tip)
        }

        return NumberFormat
            .getCurrencyInstance()
            .format(tip)
    }
}
