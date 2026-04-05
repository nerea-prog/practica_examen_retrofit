package com.example.practica_examen.network

import com.example.practica_examen.model.Post
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("posts/posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("posts/posts/{id}")
    suspend fun getPostById(@Path("id") id: String): Response<Post>

    @POST("posts/posts")
    suspend fun createPost(@Body post: Post): Response<Post>

    @PUT("posts/posts/{id}/")
    suspend fun updatePostById(@Path("id") id: String, @Body post: Post): Response<Post>

    @DELETE("posts/posts/{id}/")
    suspend fun eliminarItem(@Path("id") id: String): Response<Unit>
}