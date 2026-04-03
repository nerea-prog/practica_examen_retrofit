package com.example.practica_examen.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica_examen.R
import com.example.practica_examen.adapter.PostAdapter
import com.example.practica_examen.viewmodel.PostViewModel

class Ejercicio1Activity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()
    private lateinit var adapter: PostAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio1)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)

        // Adaptamos el constructor del Adapter con las dos lambdas
        adapter = PostAdapter(
            onEliminar = { id ->
                viewModel.eliminar(id.toString())
            },
            onClick = { post ->
                val intent = Intent(this, DetallePostActivity::class.java)
                intent.putExtra("POST_ID", post.id)
                startActivity(intent)
            }
        )
        
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.posts.observe(this) { adapter.update(it) }
        
        viewModel.isLoading.observe(this) {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
        
        viewModel.error.observe(this) { msg ->
            msg?.let { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
        }

        // Observamos el mensaje de eliminación exitosa
        viewModel.missatge.observe(this) { msg ->
            msg?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        }
    }
}
