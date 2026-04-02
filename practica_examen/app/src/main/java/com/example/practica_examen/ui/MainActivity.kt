package com.example.practica_examen.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.practica_examen.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnEjercicio1).setOnClickListener {
            startActivity(Intent(this, Ejercicio1Activity::class.java))
        }
        findViewById<Button>(R.id.btnEjercicio2).setOnClickListener {
            startActivity(Intent(this, Ejercicio2Activity::class.java))
        }
        findViewById<Button>(R.id.btnEjercicio3).setOnClickListener {
            startActivity(Intent(this, Ejercicio1Activity::class.java)) // misma lista, al pulsar navega al detalle
        }
        findViewById<Button>(R.id.btnEjercicio4).setOnClickListener {
            startActivity(Intent(this, Ejercicio4Activity::class.java))
        }
    }
}