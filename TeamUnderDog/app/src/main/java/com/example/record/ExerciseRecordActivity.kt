package com.example.teamunderdog.record

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamunderdog.databinding.ActivityExerciseRecordBinding

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ExerciseRecordActivity : AppCompatActivity() {
    lateinit var binding: ActivityExerciseRecordBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: ExerciseRecordAdapter
    lateinit var rdb: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rdb = FirebaseDatabase.getInstance().getReference("MyRecord")
        val query = rdb.limitToLast(50)
        val option = FirebaseRecyclerOptions.Builder<ExerciseRecordData>()
            .setQuery(query, ExerciseRecordData::class.java)
            .build()
        adapter = ExerciseRecordAdapter(option)
        binding.apply {
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
        }
    }
}