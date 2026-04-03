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


class Ejercicio4Activity : AppCompatActivity() {
    private val viewModel: Ejercicio4ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio4)

        val etTitulo = findViewById<EditText>(R.id.etTitulo)
        val etBody = findViewById<EditText>(R.id.etBody)
        val btnCrear = findViewById<Button>(R.id.btnCrear)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        btnCrear.setOnClickListener {
            val titulo = etTitulo.text.toString().trim()
            val cuerpo = etBody.text.toString().trim()

            if (titulo.isNotEmpty()) {
                // Solo llamamos al ViewModel, NO limpiamos aquí todavía
                viewModel.crear(titulo, cuerpo)
            } else {
                Toast.makeText(this, "El título es obligatorio", Toast.LENGTH_SHORT).show()
            }
        }

        // Observamos el estado de carga para deshabilitar el botón
        viewModel.isLoading.observe(this) { loading ->
            btnCrear.isEnabled = !loading
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