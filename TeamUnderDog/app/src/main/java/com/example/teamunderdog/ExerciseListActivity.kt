package com.example.teamunderdog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teamunderdog.databinding.ActivityExerciseListBinding

class ExerciseListActivity : AppCompatActivity() {
    lateinit var binding: ActivityExerciseListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        TODO("Not yet implemented")
    }
}