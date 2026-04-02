package com.example.practica_examen.network

import com.example.practica_examen.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>


}