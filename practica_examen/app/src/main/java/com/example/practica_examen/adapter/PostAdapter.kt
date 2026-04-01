package com.example.practica_examen.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.practica_examen.model.Post

class PostAdapter(private var lista: List<Post> = emptyList()) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textNombre: TextView = view.findViewById(R.id.textNombre)
        val textPrecio: TextView = view.findViewById(R.id.textPrecio)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        holder.textNombre.text = item.nombre
        holder.textPrecio.text = "${item.precio} EUR"
    }


    override fun getItemCount() = lista.size


    // Llamar este método desde la Activity para actualizar los datos
    fun actualizarLista(nuevaLista: List<Producto>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
