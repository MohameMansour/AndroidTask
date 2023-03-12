package com.example.task.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.task.R
import com.example.task.databinding.FragmentPostsBinding
import com.example.task.presentation.viewmodel.PostViewModel
import com.example.task.presentation.adapter.PostAdapter
import com.example.task.presentation.ui.dialog.LoadingDialog
import com.example.task.util.showToast
import com.example.task.util.state.UiState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts){

    @Inject
    lateinit var postAdapter: PostAdapter
    private lateinit var binding: FragmentPostsBinding
    private val viewModel by viewModels<PostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostsBinding.bind(view)

        binding.rvPost.adapter = postAdapter
        fetchPostState()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getPosts()
    }

    private fun fetchPostState() {
        lifecycleScope.launchWhenStarted {
            viewModel.postsState.collect { state ->
                when (state) {
                    is UiState.Success -> {
                        LoadingDialog.dismissDialog()
                        postAdapter.submitList(state.data)
                    }
                    is UiState.Error -> {
                        LoadingDialog.dismissDialog()
                        showToast(state.message!!.asString(requireContext()))
                    }
                    is UiState.Loading -> {
                        LoadingDialog.showDialog()
                    }
                    else -> Unit
                }
            }
        }
    }

}