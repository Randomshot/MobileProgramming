package com.example.teamunderdog.record

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        val rdbpath = "MyRecord/items"
        rdb = FirebaseDatabase.getInstance().getReference(rdbpath)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val query = rdb.limitToLast(50)
        val option = FirebaseRecyclerOptions.Builder<ExerciseRecordData>()
                .setQuery(query, ExerciseRecordData::class.java)
                .build()
        adapter = ExerciseRecordAdapter(option)
        binding.apply {
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
        }
        adapter.startListening()

        val simpleCallback = object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.DOWN or ItemTouchHelper.UP,
                ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                rdb.child(adapter.getItem(viewHolder.adapterPosition).eId.toString()).removeValue()

            }

        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }
}
