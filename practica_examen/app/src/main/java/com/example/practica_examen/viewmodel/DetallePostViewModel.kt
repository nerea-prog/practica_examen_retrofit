package com.example.practica_examen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica_examen.model.Post
import com.example.practica_examen.repository.PostRepository
import kotlinx.coroutines.launch

class DetallePostViewModel : ViewModel() {
    private val repository = PostRepository()

    private val _post = MutableLiveData<Post?>()   // UN sol element, no llista
    val post: LiveData<Post?> = _post

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun cargarItem(id: Int) {   // El ID ve des de l'Activity
        viewModelScope.launch {
            try {
                val response = repository.getPostById(id)
                if (response.isSuccessful) {
                    _post.value = response.body()
                } else {
                    _error.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de connexió: ${e.message}"
            }
        }
    }
}
