package com.example.task.data.remote

import com.example.task.data.remote.dto.PostDto
import retrofit2.Response
import retrofit2.http.GET

interface PostApi {

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }

    @GET("posts")
    suspend fun getPosts(): Response<List<PostDto>>

}