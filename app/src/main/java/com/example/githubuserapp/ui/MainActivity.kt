package com.example.githubuserapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.google.gson.internal.GsonBuildConfig

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}