package com.example.practica_examen.network

import com.example.practica_examen.model.Post
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("posts/")
    suspend fun getAllPost(): Response<List<Post>>
}