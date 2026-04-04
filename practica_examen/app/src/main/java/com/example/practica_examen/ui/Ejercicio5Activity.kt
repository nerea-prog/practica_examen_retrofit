package com.example.practica_examen.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.practica_examen.R
import com.example.practica_examen.viewmodel.Ejercicio4ViewModel
import android.view.View

import android.widget.ProgressBar
import com.example.practica_examen.viewmodel.Ejercicio5ViewModel


class Ejercicio5Activity : AppCompatActivity() {
    private val viewModel: Ejercicio5ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio5)

        val etId = findViewById<EditText>(R.id.etId)
        val etTitulo = findViewById<EditText>(R.id.etTituloUpdate)
        val etBody = findViewById<EditText>(R.id.etBodyUpdate)
        val btnActualizar = findViewById<Button>(R.id.btnActualizar)
        val progressBar = findViewById<ProgressBar>(R.id.progressBarUpdate)

        btnActualizar.setOnClickListener {
            val id = etId.text.toString().trim()
            val titulo = etTitulo.text.toString().trim()
            val cuerpo = etBody.text.toString().trim()

            if (id.isNotEmpty() && titulo.isNotEmpty() && cuerpo.isNotEmpty()) {
                // Solo llamamos al ViewModel, NO limpiamos aquí todavía
                viewModel.actualitzar(id, titulo, cuerpo)
            } else {
                Toast.makeText(this, "Es obligatorio rellenar toda la información", Toast.LENGTH_SHORT).show()
            }
        }

        // Observamos el estado de carga para deshabilitar el botón
        viewModel.isLoading.observe(this) { loading ->
            btnActualizar.isEnabled = !loading
            progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        // Observamos el mensaje para saber cuándo limpiar
        viewModel.missatge.observe(this) { msg ->
            msg?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                if (it.contains("correctament")) {
                    etTitulo.text.clear()
                    etBody.text.clear()
                }
            }
        }
    }
}