package com.example.calculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private var index: Int = 0;
    private val expresion = StringBuilder();
    private val resultText = StringBuilder();
    private var result: Double = 0.0;

    fun appendExpression(str: String) {
        expresion.append(str);
    }

    fun removeLastCharacter() {
        expresion.deleteCharAt(expresion.length - 1);
    }

    fun clearEntry() {
        expresion.clear();
    }

    fun clear() {
        expresion.clear();
        resultText.clear();
    }

    fun additionFunction(str1: String, str2: String): Double {
        val num1 = str1.toDouble();
        val num2 = str2.toDouble();

        return num1 + num2;
    }

    fun subtractionFunction(str1: String, str2: String): Double {
        val num1 = str1.toDouble();
        val num2 = str2.toDouble();

        return num1 - num2;
    }

    fun multiplicationFunction(str1: String, str2: String): Double {
        val num1 = str1.toDouble();
        val num2 = str2.toDouble();

        return num1 * num2;
    }

    fun divisionFunction(str1: String, str2: String): Double? {
        return try {
            val num1 = str1.toDouble();
            val num2 = str2.toDouble();

            if (num2 == 0.0) {
                resultText.clear();
                resultText.append("Error: Division by zero");
                null;
            } else {
                num1 / num2;
            }
        } catch (e: NumberFormatException) {
            resultText.clear();
            resultText.append("Error: Invalid input");
            null;
        }
    }

    fun percentageFunction(): Double? {
        return null;
    }

    fun equalFunction(): Unit {
        splitedExpression();
    }

    fun splitedExpression(): Unit {

    }

    fun calculateExpression(str: String) {
        when (str) {
            "+" -> additionFunction("1", "2");
            "-" -> subtractionFunction("1", "2");
            "*" -> multiplicationFunction("1", "2");
            "/" -> divisionFunction("1", "2");
            "%" -> percentageFunction();
        }
    }


}