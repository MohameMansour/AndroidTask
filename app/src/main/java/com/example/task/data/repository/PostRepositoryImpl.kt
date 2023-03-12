package com.example.task.data.repository

import com.example.task.data.remote.PostApi
import com.example.task.data.remote.dto.PostDto
import com.example.task.domain.repository.PostsRepository
import com.example.task.util.state.ApiState
import com.example.task.util.state.Resource
import com.example.task.util.toResultFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostRepositoryImpl(private val postApi: PostApi) : PostsRepository {

    override suspend fun getUserPosts(): Flow<Resource<List<PostDto>>> {

        return flow {
            val result = toResultFlow { postApi.getPosts() }
            result.collect { apiState ->
                when (apiState) {
                    is ApiState.Loading -> emit(Resource.Loading())
                    is ApiState.Error -> emit(Resource.Error(apiState.message!!))
                    is ApiState.Success -> emit(Resource.Success(apiState.data))
                }
            }
        }

    }

}