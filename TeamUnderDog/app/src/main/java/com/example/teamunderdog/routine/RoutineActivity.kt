package com.example.teamunderdog.routine

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamunderdog.routine.SportsActivity
import com.example.teamunderdog.databinding.ActivityRoutineBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RoutineActivity : AppCompatActivity() {
    lateinit var binding: ActivityRoutineBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MyRoutineAdapter
    lateinit var rdb: DatabaseReference
    var findQuery = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoutineBinding.inflate(layoutInflater)
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
            override fun OnItemClick(view: View, position: Int) {
                binding.rIdEdit.setText(adapter.getItem(position).rId.toString())
                binding.rNameEdit.setText(adapter.getItem(position).rName.toString())
                val intent = Intent(applicationContext, SportsActivity::class.java)
                intent.putExtra("routine_no", adapter.getItem(position).rId)
                intent.putExtra("routine_name", adapter.getItem(position).rName)
                startActivity(intent)
            }
        }

        binding.apply{
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter

            insertrtn.setOnClickListener {
                initAdapter()
                val item = Routine(rIdEdit.text.toString().toInt(),
                        rNameEdit.text.toString(), mutableListOf())
                rdb.child(rIdEdit.text.toString()).setValue(item)
                clearInput()
            }
            //
            findrtn.setOnClickListener {
                if(!findQuery)
                    findQuery = true
                if(adapter!=null)
                    adapter.stopListening() // 질의 동기화 멈추기
                val query = rdb.orderByChild("rname").equalTo(rNameEdit.text.toString())
                val option = FirebaseRecyclerOptions.Builder<Routine>()
                    .setQuery(query, Routine::class.java)
                    .build()
                adapter = MyRoutineAdapter(option)
                adapter.itemClickListener = object : MyRoutineAdapter.OnItemClickListener{
                    override fun OnItemClick(view: View, position: Int) {
                        binding.rIdEdit.setText(adapter.getItem(position).rId.toString())
                        binding.rNameEdit.setText(adapter.getItem(position).rName.toString())
                        val intent = Intent(applicationContext, SportsActivity::class.java)
                        intent.putExtra("routine_no", adapter.getItem(position).rId)
                        intent.putExtra("routine_name", adapter.getItem(position).rName)
                        startActivity(intent)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }

            deletertn.setOnClickListener {
                initAdapter()
                rdb.child(rIdEdit.text.toString()).removeValue()
                clearInput()
            }

            updatertn.setOnClickListener {
                initAdapter()
                rdb.child(rIdEdit.text.toString())
                    .child("rname")
                    .setValue(rNameEdit.text.toString())
                clearInput()
            }
        }
    }

    fun initAdapter(){
        if(findQuery){
            findQuery = false
            if(adapter!=null)
                adapter.startListening()
            val query = rdb.limitToLast(50) // 최근 50
            //val query = rdb.orderByKey()
            val option = FirebaseRecyclerOptions.Builder<Routine>()
                .setQuery(query, Routine::class.java)
                .build()
            adapter = MyRoutineAdapter(option)
            adapter.itemClickListener = object : MyRoutineAdapter.OnItemClickListener{
                override fun OnItemClick(view: View, position: Int) {
                    binding.rIdEdit.setText(adapter.getItem(position).rId.toString())
                    binding.rNameEdit.setText(adapter.getItem(position).rName.toString())
                    val intent = Intent(applicationContext, SportsActivity::class.java)
                    intent.putExtra("routine_no", adapter.getItem(position).rId)
                    intent.putExtra("routine_name", adapter.getItem(position).rName)
                    startActivity(intent)
                }
            }
            binding.recyclerView.adapter = adapter
            adapter.startListening()
        }
    }

    fun clearInput(){
        binding.apply {
            rIdEdit.text.clear()
            rNameEdit.text.clear()
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