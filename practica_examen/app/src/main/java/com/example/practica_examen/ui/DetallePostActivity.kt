package com.example.practica_examen.ui

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.practica_examen.R
import com.example.practica_examen.viewmodel.DetallePostViewModel

class DetallePostActivity : AppCompatActivity() {
    private val viewModel: DetallePostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_post)

        // Ahora recibimos un String
        val postId = intent.getStringExtra("POST_ID")
        if (postId == null) { finish(); return }

        // Carregar les dades (pasamos el String directamente)
        viewModel.cargarItem(postId)

        viewModel.post.observe(this) { post ->
            post?.let {
                findViewById<TextView>(R.id.tvIdDetalle).text = it.id
                findViewById<TextView>(R.id.tvTituloDetalle).text = it.title
                findViewById<TextView>(R.id.tvBodyDetalle).text = it.body
            }
        }

        viewModel.error.observe(this) { msg ->
            msg?.let { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
        }
    }
}
