package com.example.teamunderdog

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.exercisetips.TipsMainActivity
import com.example.teamunderdog.databinding.ActivityMainBinding
import com.example.teamunderdog.exerciselist.ExerciseListActivity
import com.example.teamunderdog.routine.RoutineActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {

        binding.button.setOnClickListener {

            val intent = Intent(this, ExerciseListActivity::class.java)
            startActivity(intent)
        }

        binding.button2.setOnClickListener {
            val intent = Intent(this, RoutineActivity::class.java)
            startActivity(intent)
        }
        binding.button3.setOnClickListener {
            val intent = Intent(this, TipsMainActivity::class.java)
            startActivity(intent)
        }
    }
}