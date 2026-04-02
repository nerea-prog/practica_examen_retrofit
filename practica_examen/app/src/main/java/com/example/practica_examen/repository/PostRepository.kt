package com.example.practica_examen.repository

import android.util.Log
import com.example.practica_examen.model.Post
import com.example.practica_examen.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class PostRepository {

    suspend fun getPosts(): Response<List<Post>> {
        return RetrofitClient.API().getPosts()
    }
}