package com.example.practica_examen.repository

import android.util.Log
import com.example.practica_examen.model.Post
import com.example.practica_examen.network.ApiService
import com.example.practica_examen.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class PostRepository {

    suspend fun getPosts(): Response<List<Post>> {
        return RetrofitClient.API().getPosts()
    }
    suspend fun getPostById(id: String): Response<Post> = RetrofitClient.API().getPostById(id)

    suspend fun createPost(post: Post): Response<Post> = RetrofitClient.API().createPost(post)

    suspend fun updatePostById(id: String, post: Post): Response<Post> = RetrofitClient.API().updatePostById(id, post)
    suspend fun eliminar(id: String): Response<Unit> = RetrofitClient.API().eliminarItem(id)
}
