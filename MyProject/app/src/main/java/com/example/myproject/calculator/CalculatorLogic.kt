package com.example.myproject.calculator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.function.Function
import java.text.DecimalFormat
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.atan

// Functions from your original code
val factorial = object : Function("fact", 1) {
    override fun apply(vararg args: Double): Double {
        // ... (your factorial logic)
        val n = args[0].toInt()
        if (n < 0 || n != args[0].toInt()) {
            throw IllegalArgumentException("Argument for factorial must be a non-negative integer")
        }
        var result = 1.0
        for (i in 2..n) {
            result *= i
        }
        return result
    }
}
val myAsin = object : Function("asin", 1) { override fun apply(vararg args: Double) = asin(args[0]) }
val myAcos = object : Function("acos", 1) { override fun apply(vararg args: Double) = acos(args[0]) }
val myAtan = object : Function("atan", 1) { override fun apply(vararg args: Double) = atan(args[0]) }

class CalculatorViewModel : ViewModel() {
    var display by mutableStateOf("0")
        private set
    var expression by mutableStateOf("")
        private set
    var isInverse by mutableStateOf(false)
        private set
    var isScientific by mutableStateOf(false)
        private set

    private fun evaluateExpression(exp: String): String {
        // ... (your evaluation logic)
        return try {
            val sanitizedExp = exp
                .replace("×", "*")
                .replace("÷", "/")
                .replace("√", "sqrt")
                .replace("reciproc(", "(1/")
                .replace("xʸ", "^")
                .replace("sin⁻¹", "asin")
                .replace("cos⁻¹", "acos")
                .replace("tan⁻¹", "atan")
                .replace("C", "")

            val expressionBuilder = ExpressionBuilder(sanitizedExp)
                .function(factorial)
                .function(myAsin)
                .function(myAcos)
                .function(myAtan)
                .build()

            val result = expressionBuilder.evaluate()
            DecimalFormat("#.#######").format(result)
        } catch (e: Exception) {
            "Error"
        }
    }

    fun onButtonClick(buttonText: String) {
        // ... (your button handling logic)
        var currentText = buttonText

        // Handle tombol Scientific/Standard toggle
        if (buttonText == "SCIENTIFIC_TOGGLE") {
            isScientific = !isScientific
            return
        }

        // Handle tombol inverse
        if (isInverse) {
            currentText = when (buttonText) {
                "sin" -> "sin⁻¹"
                "cos" -> "cos⁻¹"
                "tan" -> "tan⁻¹"
                else -> buttonText
            }
        }

        // Sesuaikan nama tombol untuk logika evaluasi
        val logicText = when (currentText) {
            "C" -> "AC"
            "⌫" -> "⌫"
            "," -> "." // Koma menjadi titik
            "00" -> "00"
            "sin⁻¹", "cos⁻¹", "tan⁻¹" -> currentText
            else -> currentText
        }

        when (logicText) {
            "AC" -> {
                expression = ""
                display = "0"
            }
            "⌫" -> { // Backspace
                if (expression.isNotEmpty()) {
                    expression = expression.dropLast(1)
                    display = if (expression.isEmpty()) "0" else expression
                }
            }
            "=" -> {
                if (expression.isNotEmpty()) {
                    val result = evaluateExpression(expression)
                    display = result
                    expression = if (result != "Error") result else ""
                }
            }
            "inv" -> {
                isInverse = !isInverse
            }
            // ... (rest of your logic for "1/x", "sin", "cos", "tan", "xʸ", ".", and "else")
            "1/x" -> {
                if (expression == "0" || expression == "Error" || expression.isEmpty()) {
                    expression = "reciproc("
                } else {
                    expression += "reciproc("
                }
                display = expression
            }
            "sin", "cos", "tan", "log", "ln", "√", "!", "sin⁻¹", "cos⁻¹", "tan⁻¹" -> {
                if (expression == "0" || expression == "Error" || expression.isEmpty()) {
                    expression = "$logicText("
                } else {
                    expression += "$logicText("
                }
                display = expression
            }
            "xʸ" -> {
                expression += "^"
                display = expression
            }
            "." -> {
                val lastChar = expression.lastOrNull()
                if (lastChar == null || !lastChar.isDigit()) {
                    expression += "0."
                } else {
                    val lastOperatorIndex = expression.lastIndexOfAny(charArrayOf('+', '-', '×', '÷', '^', '(', ')'))
                    val currentNumber = if (lastOperatorIndex != -1) expression.substring(lastOperatorIndex + 1) else expression
                    if (!currentNumber.contains('.')) {
                        expression += "."
                    }
                }
                display = expression
            }
            else -> {
                if (display == "0" || expression == "Error" || expression.isEmpty()) {
                    expression = logicText
                } else {
                    expression += logicText
                }
                display = expression
            }
        }
    }
}

// Now you can define a wrapper composable that ties the VM and the UI together
@Composable
fun CalculatorApp(
    viewModel: CalculatorViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onBack: () -> Unit
) {
    CalculatorScreen(
        display = viewModel.display,
        isScientific = viewModel.isScientific,
        isInverse = viewModel.isInverse,
        onButtonClick = viewModel::onButtonClick,
        onBack = onBack
    )
}