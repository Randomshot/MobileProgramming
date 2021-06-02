package com.example.teamunderdog.routine

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamunderdog.databinding.ActivityRoutineExerciseBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RoutineExerciseActivity : AppCompatActivity() {

    lateinit var binding: ActivityRoutineExerciseBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MySportsAdapter
    lateinit var rdb: DatabaseReference
    lateinit var routineid:String
    lateinit var routinename:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoutineExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        routineid = intent.getIntExtra("routine_no",0).toString()
        routinename = intent.getStringExtra("routine_name")!!
        init()
    }

    private fun init() {
        var path = "Routines/items/"
        path = path + routineid +"/sportslist"
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rdb = FirebaseDatabase.getInstance().getReference(path)
        val query = rdb.limitToLast(50) // 최근 50
        //val query = rdb.orderByKey()
        val option = FirebaseRecyclerOptions.Builder<Sports>()
                .setQuery(query, Sports::class.java)
                .build()
        adapter = MySportsAdapter(option)
        adapter.itemClickListener = object : MySportsAdapter.OnItemClickListener{
            override fun OnItemClick(view: View, position: Int) { // 운동 종목 클릭
                binding.apply {
                }
            }
        }
        binding.apply{
            sportsrecyclerView.layoutManager = layoutManager
            sportsrecyclerView.adapter = adapter
            Routinename.text = "번호 : " + routineid + "\n이름 : " + routinename
        }

    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}