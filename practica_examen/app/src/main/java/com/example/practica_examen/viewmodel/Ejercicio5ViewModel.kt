package com.example.practica_examen.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica_examen.model.Post
import com.example.practica_examen.repository.PostRepository
import kotlinx.coroutines.launch

class Ejercicio5ViewModel : ViewModel() {
    private val repository = PostRepository()

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    private val _missatge = MutableLiveData<String?>()
    val missatge: LiveData<String?> = _missatge
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

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

    fun actualitzar(id: String, title: String, body: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val itemActualitzat = Post(id = id, userId = "0", title = title, body = body)
                val response = repository.updatePostById(id, itemActualitzat)
                if (response.isSuccessful) {
                    _missatge.value = "Actualitzat correctament!"
                    cargar()  // ← Recarregar la llista
                } else {
                    _missatge.value = "Error al actualitzar: ${response.code()}"
                }
            } catch (e: Exception) {
                _missatge.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

}