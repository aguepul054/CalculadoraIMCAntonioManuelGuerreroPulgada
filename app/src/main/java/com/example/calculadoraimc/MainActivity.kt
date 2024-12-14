package com.example.calculadoraimc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    // Inicialización de variables por defecto para el peso (60kg) y altura (120cm)
    private var currentWeight = 60
    private var currentHeight: Int = 120

    // Inicialización de las vistas de la interfaz para interactuar con ellas
    private lateinit var viewMale: CardView
    private lateinit var viewFemale: CardView
    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider
    private lateinit var tvWeight: TextView
    private lateinit var tvAge: TextView

    // Botones para las acciones de la interfaz
    private lateinit var btnCalculate: Button
    private lateinit var btnSubstractW: FloatingActionButton
    private lateinit var btnPlusW: FloatingActionButton
    private lateinit var btnSubstractA: FloatingActionButton
    private lateinit var btnPlusA: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita un diseño a pantalla completa
        setContentView(R.layout.activity_main)

        // Configuración para la compatibilidad con las barras del sistema en la interfaz
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicialización de los componentes de la interfaz
        viewMale = findViewById(R.id.viewMale)
        viewFemale = findViewById(R.id.viewFemale)
        tvHeight = findViewById(R.id.tvHeight)
        rsHeight = findViewById(R.id.rsHeight)
        tvWeight = findViewById(R.id.tvWeight)
        tvAge = findViewById(R.id.tvAge)
        btnSubstractW = findViewById(R.id.btnSubstractW)
        btnPlusW = findViewById(R.id.btnAddW)
        btnSubstractA = findViewById(R.id.btnSubstractA)
        btnPlusA = findViewById(R.id.btnAddA)
        btnCalculate = findViewById(R.id.btnCalc)

        // Acción para seleccionar el género masculino
        viewMale.setOnClickListener {
            viewMale.setCardBackgroundColor(resources.getColor(R.color.pulsadoGenre, null)) // Cambio de color al seleccionar
            viewFemale.setCardBackgroundColor(resources.getColor(R.color.cv__blue, null))  // Resetea el color del otro género
        }

        // Acción para seleccionar el género femenino
        viewFemale.setOnClickListener {
            viewFemale.setCardBackgroundColor(resources.getColor(R.color.pulsadoGenre, null)) // Cambio de color al seleccionar
            viewMale.setCardBackgroundColor(resources.getColor(R.color.cv__blue, null)) // Resetea el color del otro género
        }

        // Configura el RangeSlider para ajustar la altura, actualizando el TextView correspondiente
        rsHeight.addOnChangeListener { _, value, _ ->
            val fmt = DecimalFormat("#.##") // Formato para limitar a 2 decimales
            currentHeight = fmt.format(value).toInt() // Formateo y conversión de la altura a entero
            tvHeight.text = "$currentHeight cm" // Actualización del TextView
        }

        // Configura el botón para disminuir el peso
        btnSubstractW.setOnClickListener {
            var cN = tvWeight.text.toString().toInt() // Obtiene el valor actual del peso
            if (cN > 18) { // Evita que el peso sea menor a 18
                cN-- // Disminuye el peso
                tvWeight.text = cN.toString() // Actualiza el TextView del peso
            }
        }

        // Configura el botón para aumentar el peso
        btnPlusW.setOnClickListener {
            var cN = tvWeight.text.toString().toInt() // Obtiene el valor actual del peso
            if (cN < 170) { // Evita que el peso sea mayor a 170
                cN++ // Aumenta el peso
                tvWeight.text = cN.toString() // Actualiza el TextView del peso
            }
        }

        // Configura el botón para disminuir la edad
        btnSubstractA.setOnClickListener {
            var cA = tvAge.text.toString().toInt() // Obtiene el valor actual de la edad
            if (cA > 6) { // Evita que la edad sea menor a 6
                cA-- // Disminuye la edad
                tvAge.text = cA.toString() // Actualiza el TextView de la edad
            }
        }

        // Configura el botón para aumentar la edad
        btnPlusA.setOnClickListener {
            var cA = tvAge.text.toString().toInt() // Obtiene el valor actual de la edad
            if (cA < 100) { // Evita que la edad sea mayor a 100
                cA++ // Aumenta la edad
                tvAge.text = cA.toString() // Actualiza el TextView de la edad
            }
        }

        // Configura el botón para calcular el IMC
        btnCalculate.setOnClickListener {
            currentWeight = tvWeight.text.toString().toInt() // Obtiene el peso actual
            val result = calculateIMC() // Calcula el IMC
            val intent = Intent(this, ResultActivity::class.java) // Crea una nueva intención para ir a la siguiente actividad
            intent.putExtra("resultadoIMC", result) // Pasa el resultado del IMC a la siguiente actividad
            startActivity(intent) // Inicia la actividad ResultActivity
        }
    }

    // Función para calcular el IMC
    private fun calculateIMC(): Double {
        val df = DecimalFormat("#.##") // Formato para limitar el resultado del IMC a 2 decimales
        val imcRes = currentWeight / (currentHeight.toDouble() / 100 * currentHeight.toDouble() / 100) // Cálculo del IMC
        val result = df.format(imcRes.toDouble()) // Formatea el resultado
        return result.toDouble() // Devuelve el IMC como valor numérico
    }
}
