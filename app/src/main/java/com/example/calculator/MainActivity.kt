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
    private lateinit var visor: TextView
    private val expression = StringBuilder("(1+(2*3))-(4/5)")
    private val resultText = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        visor = findViewById(R.id.text_visor)
        visor.text = expression.toString()
    }

    fun appendExpression(view: View) {
        val button = view as Button
        val buttonText = button.text.toString()
        expression.append(buttonText)
        visor.text = expression.toString()

    }

    fun removeLastCharacter(view: View) {
        if (expression.isNotEmpty()) {
            expression.deleteCharAt(expression.length - 1)
            visor.text = expression.toString()
        }
    }

    fun clearEntry(view: View) {
        expression.clear()
        visor.text = ""
    }

    fun clear(view: View) {
        expression.clear()
        resultText.clear()
        visor.text = ""
    }

    // Funções de operação refatoradas para aceitar Doubles
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
        val tree = buildExpressionTree(expression.toString());
        val finalResult = calculateFinalResult(tree)
        println(finalResult);
        if (finalResult != null) {
            visor.text =finalResult.toString()
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

                // 3. Se qualquer um dos lados falhou (retornou null), propaga o erro.
                if (leftResult == null || rightResult == null) {
                    resultText.clear()
                    resultText.append("Error: Invalid input")
                    return null
                }

                // 4. Com os resultados da esquerda e direita em mãos, aplica o operador.
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