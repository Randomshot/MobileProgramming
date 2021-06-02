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
import java.time.LocalDate

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
    private fun initRecycler(date: String){
        val rdbpath = "MyRecord/"+date
        var total = 0
        var num =0
        rdb = FirebaseDatabase.getInstance().getReference(rdbpath)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val query = rdb.limitToLast(50)
        val option = FirebaseRecyclerOptions.Builder<ExerciseRecordData>()
                .setQuery(query, ExerciseRecordData::class.java)
                .build()
        adapter = ExerciseRecordAdapter(option)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        /*for (i : Int in 1..adapter.itemCount){
            total += adapter.getItem(i-1).eRecord
        }*/  //이부분이 왜 안될까요???
        binding.totalRecord.setText("총 운동 시간 : "+(total / 60).toString() + "분 "+(total%60).toString()+"초")
        adapter.startListening()
    }
    private fun init() {
        var date = LocalDate.now().toString()

        initRecycler(date)

        binding.datePicker2.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            adapter.stopListening()
            if(monthOfYear>=9&&dayOfMonth>=9){
                date = year.toString()+"-"+(monthOfYear+1).toString()+"-"+dayOfMonth.toString()
            }
            else if (monthOfYear <10 && dayOfMonth >= 9){
                date = year.toString()+"-0"+(monthOfYear+1).toString()+"-"+dayOfMonth.toString()
            }
            else{
                 date = year.toString()+"-0"+(monthOfYear+1).toString()+"-0"+dayOfMonth.toString()
            }
            initRecycler(date)
        }

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

