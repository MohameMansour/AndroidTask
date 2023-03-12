package com.example.task.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.data.remote.dto.PostDto
import com.example.task.domain.repository.PostsRepository
import com.example.task.util.state.Resource
import com.example.task.util.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postsRepository: PostsRepository
) : ViewModel() {

    private val _postState = MutableStateFlow<UiState<List<PostDto>>>(UiState.Empty())
    val postsState: StateFlow<UiState<List<PostDto>>> = _postState

    private var postsJob: Job? = null

    fun cancelRequest() {
        postsJob?.cancel()
    }

    fun getPosts() {
        postsJob?.cancel()
        postsJob = viewModelScope.launch {
            delay(450L)
            withContext(coroutineContext) {
                postsRepository.getUserPosts().collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { data ->
                                _postState.value = UiState.Success(data)
                            } ?: run { _postState.value = UiState.Empty() }
                        }
                        is Resource.Error -> {
                            _postState.value =
                                UiState.Error(result.message!!)
                        }
                        is Resource.Loading -> {
                            _postState.value = UiState.Loading()
                        }
                    }
                }
            }
        }
    }

}