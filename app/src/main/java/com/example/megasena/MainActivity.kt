package com.example.megasena

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.*
import org.w3c.dom.Text
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editNumber: EditText = findViewById(R.id.txt_editNumber)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerator: Button = findViewById(R.id.btn_generator)

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)
        result?.let {
            txtResult.text = "Última aposta: $result"
        }

        btnGenerator.setOnClickListener {
            val text = editNumber.text.toString()
            numberGenerator(text, txtResult)
        }

    }

    private fun numberGenerator(text: String, txtResult: TextView) {
        if (text.isEmpty()) {
            Toast.makeText(this, "INFORME UM NÚMERO ENTRE 6 E 15!", Toast.LENGTH_LONG).show()
            return
        }
        val qtd = text.toInt()

        if (qtd < 6 || qtd > 15) {
            Toast.makeText(this, "INFORME UM NÚMERO ENTRE 6 E 15!", Toast.LENGTH_LONG).show()
            return
        }

        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (true) {
            val number = random.nextInt(60) //0..59
            numbers.add(number + 1)

            if (numbers.size == qtd) {
                break
            }
        }
        txtResult.text = numbers.joinToString(" - ")
        prefs.edit().apply {
            putString("result", txtResult.text.toString())
            apply()
        }

    }
}