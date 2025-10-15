package com.example.calculator

import ExpressionNode
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import buildExpressionTree

class MainActivity : AppCompatActivity() {
    private lateinit var eVisor: TextView
    private lateinit var rVisor: TextView
    private val expression = StringBuilder("(1+(2*3))-(4/5)")
    private val resultText = StringBuilder("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        eVisor = findViewById(R.id.expression_visor)
        eVisor.text = expression.toString()

        rVisor = findViewById(R.id.result_visor)
        rVisor.text = resultText.toString()


    }

    fun appendExpression(view: View) {
        val button = view as Button
        val buttonText = button.text.toString()
        expression.append(buttonText)
        eVisor.text = expression.toString()

    }

    fun removeLastCharacter(view: View) {
        if (expression.isNotEmpty()) {
            expression.deleteCharAt(expression.length - 1)
            eVisor.text = expression.toString()
        }
    }

    fun clearEntry(view: View) {
        expression.clear()
        eVisor.text = ""
    }

    fun clear(view: View) {
        expression.clear()
        resultText.clear()
        eVisor.text = ""
    }

    fun additionFunction(num1: Double, num2: Double): Double {
        return num1 + num2
    }

    fun subtractionFunction(num1: Double, num2: Double): Double {
        return num1 - num2
    }

    fun multiplicationFunction(num1: Double, num2: Double): Double {
        return num1 * num2
    }

    fun divisionFunction(num1: Double, num2: Double): Double? {
        return if (num2 == 0.0) {
            null
        } else {
            num1 / num2
        }
    }

    fun percentageFunction(): Double? {
        return null
    }

fun equalFunction(view: View) {
    val tree = buildExpressionTree(expression.toString())
    val finalResult = calculateFinalResult(tree)
    if (finalResult != null) {
        expression.clear()
        expression.append(finalResult.toString())

        resultText.clear()
        resultText.append(finalResult.toString())
        rVisor.text = resultText.toString()
    } else {
        rVisor.text = "Error: Invalid input"
    }
}

    fun calculateFinalResult(node: ExpressionNode): Double? {
        when (node) {
            is ExpressionNode.NumberNode -> {
                return node.value
            }

            is ExpressionNode.OperatorNode -> {
                val leftResult = calculateFinalResult(node.left)

                val rightResult = calculateFinalResult(node.right)

                if (leftResult == null || rightResult == null) {
                    resultText.clear()
                    resultText.append("Error: Invalid input")
                    return null
                }

                return when (node.operator) {
                    '+' -> additionFunction(leftResult, rightResult)
                    '-' -> subtractionFunction(leftResult, rightResult)
                    '*' -> multiplicationFunction(leftResult, rightResult)
                    '/' -> divisionFunction(leftResult, rightResult)
                    else -> {
                        resultText.clear()
                        resultText.append("Error: Unknown operator '${node.operator}'")
                        null
                    }
                }
            }
        }
    }

}