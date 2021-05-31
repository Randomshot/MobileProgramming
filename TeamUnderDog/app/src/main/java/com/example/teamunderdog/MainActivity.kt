package com.example.teamunderdog

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.exercisetips.TipsMainActivity
import com.example.teamunderdog.databinding.ActivityMainBinding
import com.example.teamunderdog.exerciselist.ExerciseListActivity
import com.example.teamunderdog.record.ExerciseRecordActivity
import com.example.teamunderdog.record.ShowPhysicalRecordActivity
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

        binding.apply {
            button.setOnClickListener {

                val intent = Intent(this@MainActivity, ExerciseListActivity::class.java)
                startActivity(intent)
            }
            button2.setOnClickListener {

                val intent = Intent(this@MainActivity, TipsMainActivity::class.java)
                startActivity(intent)
            }
            button3.setOnClickListener {
                val intent = Intent(this@MainActivity, RoutineActivity::class.java)
                startActivity(intent)
            }

            button4.setOnClickListener {
                val intent = Intent(this@MainActivity, ExerciseRecordActivity::class.java)
                startActivity(intent)
            }

            button5.setOnClickListener {
                val intent = Intent(this@MainActivity, ShowPhysicalRecordActivity::class.java)
                startActivity(intent)
            }

        }

    }


}
