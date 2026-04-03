package com.example.practica_examen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica_examen.model.Post
import com.example.practica_examen.repository.PostRepository
import kotlinx.coroutines.launch

class Ejercicio4ViewModel : ViewModel() {
    private val repository = PostRepository()

    private val _posts = MutableLiveData<List<Post>>(emptyList())
    val posts: LiveData<List<Post>> = _posts

    private val _missatge = MutableLiveData<String?>()
    val missatge: LiveData<String?> = _missatge

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init { cargar() }

    fun cargar() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = repository.getPosts()
                if (response.isSuccessful) {
                    _posts.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Error HTTP: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de connexio: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun crear(title: String, body: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val nouItem = Post(id = 0, userId = 1, title = title, body = body)
                val response = repository.createPost(nouItem)
                
                if (response.isSuccessful) {
                    val postCreado = response.body()
                    postCreado?.let { nuevo ->
                        // TRUCO: Añadimos el nuevo post a la lista local manualmente
                        val listaActual = _posts.value?.toMutableList() ?: mutableListOf()
                        listaActual.add(0, nuevo) // Lo ponemos al principio
                        _posts.value = listaActual
                    }
                    _missatge.value = "Element creat correctament!"
                } else {
                    _missatge.value = "Error al crear: ${response.code()}"
                }
            } catch (e: Exception) {
                _missatge.value = "Error de connexió: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
