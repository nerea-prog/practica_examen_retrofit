package com.example.practica_examen.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practica_examen.R
import com.example.practica_examen.adapter.PostAdapter

class DetallePostActivity : AppCompatActivity() {


    // Inicializar ViewModel (se crea automáticamente)
    private val viewModel: DetallePostActivity by viewModels()


    // Referencia al adapter del RecyclerView
    private lateinit var adapter: PostAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_post)
        configurarRecyclerView()
        observarViewModel()
    }


    private fun configurarRecyclerView() {
        adapter = PostAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }


    private fun observarViewModel() {
        viewModel..observe(this) { listaProductos ->
            adapter.submitList(listaProductos)
        }


        viewModel.isLoading.observe(this) { cargando ->
            progressBar.visibility = if (cargando) View.VISIBLE else View.GONE
        }


        viewModel.errorMessage.observe(this) { mensaje ->
            if (mensaje != null) {
                Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
            }
        }
    }
}
