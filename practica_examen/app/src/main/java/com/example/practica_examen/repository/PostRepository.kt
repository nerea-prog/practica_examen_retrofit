package com.example.practica_examen.repository

import com.example.practica_examen.network.RetrofitClient

class PostRepository {
    private val api = RetrofitClient.
    suspend fun getPosts() = api.getPosts()
}