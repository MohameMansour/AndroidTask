package com.example.task.domain.repository

import com.example.task.data.remote.dto.PostDto
import com.example.task.util.state.Resource
import kotlinx.coroutines.flow.Flow

interface PostsRepository {

    suspend fun getUserPosts(): Flow<Resource<List<PostDto>>>

}