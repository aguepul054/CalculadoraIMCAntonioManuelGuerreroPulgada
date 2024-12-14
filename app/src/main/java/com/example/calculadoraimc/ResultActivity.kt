package com.example.calculadoraimc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultActivity : AppCompatActivity() {

    // Declaración de variables que serán inicializadas más adelante en el onCreate
    private lateinit var btnRCalc: Button  // Botón para volver a la pantalla principal
    private lateinit var tvIMC: TextView   // TextView donde se mostrará el IMC calculado
    private lateinit var tvDesc: TextView  // TextView donde se muestra la descripción del IMC
    private lateinit var tvResult: TextView // TextView donde se muestra el resultado de la interpretación

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge() // Habilita el diseño de pantalla completa
        setContentView(R.layout.activity_result)  // Establece el layout de la actividad
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Ajusta los márgenes para que el contenido no quede cubierto por las barras del sistema
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicialización de las vistas (componentes de la UI)
        btnRCalc = findViewById(R.id.btnRCalc)
        tvIMC = findViewById(R.id.tvIMC)
        tvDesc = findViewById(R.id.tvDesc)
        tvResult = findViewById(R.id.tvResult)

        // Obtenemos el valor del IMC pasado desde la actividad anterior (MainActivity)
        val resultadoIMC = intent.extras?.getDouble("resultadoIMC") ?: -1.0

        // Usamos un "when" para evaluar el rango del IMC y proporcionar la descripción y el resultado
        // de acuerdo con los rangos definidos por la Organización Mundial de la Salud (OMS)
        when {
            resultadoIMC in 0.0..18.49 -> {  // IMC bajo
                tvDesc.text = getString(R.string.lowW)  // Descripción de bajo peso
                tvResult.text = getString(R.string.p_bajo)  // Texto indicando bajo peso
                tvResult.setTextColor(resources.getColor(R.color.bPeso, null))  // Cambia el color del texto
            }
            resultadoIMC in 18.5..24.99 -> {  // IMC normal
                tvDesc.text = getString(R.string.normalW)  // Descripción de peso normal
                tvResult.text = getString(R.string.p_normal)  // Texto indicando peso normal
                tvResult.setTextColor(resources.getColor(R.color.nPeso, null))  // Cambia el color del texto
            }
            resultadoIMC in 24.5..29.99 -> {  // IMC sobrepeso
                tvDesc.text = getString(R.string.heavyW)  // Descripción de sobrepeso
                tvResult.text = getString(R.string.p_sobre)  // Texto indicando sobrepeso
                tvResult.setTextColor(resources.getColor(R.color.sPeso, null))  // Cambia el color del texto
            }
            resultadoIMC >= 30.0 -> {  // IMC obesidad
                tvDesc.text = getString(R.string.obesityW)  // Descripción de obesidad
                tvResult.text = getString(R.string.p_obesidad)  // Texto indicando obesidad
                tvResult.setTextColor(resources.getColor(R.color.oPeso, null))  // Cambia el color del texto
            }
            else -> {  // Si el valor del IMC no se encuentra en ningún rango válido
                tvDesc.text = getString(R.string.errorDesc)  // Mensaje de error
            }
        }

        // Muestra el valor del IMC calculado
        tvIMC.text = resultadoIMC.toString()

        // Acción cuando el usuario presiona el botón para volver a la MainActivity
        btnRCalc.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()  // Invoca el evento de regresar a la actividad anterior
        }
    }
}
