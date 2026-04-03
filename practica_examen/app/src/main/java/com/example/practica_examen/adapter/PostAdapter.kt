package com.example.practica_examen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practica_examen.R
import com.example.practica_examen.model.Post

class PostAdapter(
    private var lista: List<Post> = emptyList(),
    private val onClick: (Post) -> Unit
) : RecyclerView.Adapter<PostAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvId: TextView = view.findViewById(R.id.tvId)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvBody: TextView = view.findViewById(R.id.tvBody)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = lista[position]
        holder.tvTitle.text = item.title
        holder.tvId.text = item.id.toString()
        holder.tvBody.text = item.body
        
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount() = lista.size

    fun update(newList: List<Post>) {
        lista = newList
        notifyDataSetChanged()
    }
}
