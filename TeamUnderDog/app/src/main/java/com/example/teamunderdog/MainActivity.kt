package com.example.teamunderdog

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercisetips.TipsMainActivity
import com.example.teamunderdog.databinding.ActivityMainBinding
import com.example.teamunderdog.exerciselist.ExerciseListActivity
import com.example.teamunderdog.routine.MyRoutineAdapter
import com.example.teamunderdog.routine.Routine
import com.example.teamunderdog.routine.RoutineActivity
import com.example.teamunderdog.routine.RoutineExerciseActivity
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MyRoutineAdapter
    lateinit var rdb: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rdb = FirebaseDatabase.getInstance().getReference("Routines/items")
        val query = rdb.limitToLast(50) // 최근 50
        //val query = rdb.orderByKey()
        val option = FirebaseRecyclerOptions.Builder<Routine>()
                .setQuery(query, Routine::class.java)
                .build()
        adapter = MyRoutineAdapter(option)
        adapter.itemClickListener = object : MyRoutineAdapter.OnItemClickListener{
            override fun OnItemClick(view: View, position: Int) { // 새롭게 액티비티 생성 할 예정
                val intent = Intent(applicationContext, RoutineExerciseActivity::class.java)
                intent.putExtra("routine_no", adapter.getItem(position).rId)
                intent.putExtra("routine_name", adapter.getItem(position).rName)
                startActivity(intent)
            }
        }

        binding.apply {
            routinerecycler.layoutManager = layoutManager
            routinerecycler.adapter = adapter
            routineupdate.setOnClickListener {
                val intent = Intent(this@MainActivity, RoutineActivity::class.java)
                startActivity(intent)
            }
            exerciseinfo.setOnClickListener {
                val intent = Intent(this@MainActivity, TipsMainActivity::class.java)
                startActivity(intent)
            }
            exercisereport.setOnClickListener {
                val intent = Intent(this@MainActivity, ExerciseListActivity::class.java)
                startActivity(intent)
            }
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
