package com.goat.weatherapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.goat.weatherapp.R
import com.goat.weatherapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, MainFragment.newInstance())
            .commit()
    }
}