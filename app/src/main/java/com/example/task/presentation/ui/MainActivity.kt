package com.example.task.presentation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.task.R
import com.example.task.databinding.ActivityMainBinding
import com.example.task.presentation.ui.dialog.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoadingDialog.init(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setup navStart
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_navHost) as NavHostFragment
        navController = navHostFragment.findNavController()

    }

}