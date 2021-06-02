package com.example.teamunderdog.routine

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamunderdog.databinding.ActivitySportsBinding
import com.example.teamunderdog.exerciselist.ExerciseData
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate

class SportsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySportsBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MySportsAdapter
    lateinit var rdb: DatabaseReference
    var findQuery = false
    lateinit var routineid:String
    lateinit var routinename:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySportsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        routineid = intent.getIntExtra("routine_no",0).toString()
        routinename = intent.getStringExtra("routine_name")!!
        init()
    }

    private fun init() {
        var path = "Routines/items/"
        path = path + routineid+"/sportslist"
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rdb = FirebaseDatabase.getInstance().getReference(path)
        val query = rdb.limitToLast(50) // 최근 50
        //val query = rdb.orderByKey()
        val option = FirebaseRecyclerOptions.Builder<Sports>()
            .setQuery(query, Sports::class.java)
            .build()
        adapter = MySportsAdapter(option)
        adapter.itemClickListener = object : MySportsAdapter.OnItemClickListener{
            override fun OnItemClick(view: View, position: Int) {
                binding.apply {
                    sIdEdit.setText(adapter.getItem(position).sId.toString())
                    sNameEdit.setText(adapter.getItem(position).sName.toString())
                    sCountEdit.setText(adapter.getItem(position).sCount.toString())
                    checkBox.isChecked = adapter.getItem(position).k
                }
            }
        }

        binding.apply{
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
            routinetext.text = "번호 : " + routineid + "\t이름 : " + routinename
            insertstn.setOnClickListener {
                initAdapter()
                val item = Sports(sIdEdit.text.toString().toInt(),
                        sNameEdit.text.toString(), checkBox.isChecked, sCountEdit.text.toString().toInt())
                rdb.child(sIdEdit.text.toString()).setValue(item)
                clearInput()
            }
            //
            findstn.setOnClickListener {
                if(!findQuery)
                    findQuery = true
                if(adapter!=null)
                    adapter.stopListening() // 질의 동기화 멈추기
                val query = rdb.orderByChild("sname").equalTo(sNameEdit.text.toString())
                val option = FirebaseRecyclerOptions.Builder<Sports>()
                    .setQuery(query, Sports::class.java)
                    .build()
                adapter = MySportsAdapter(option)
                adapter.itemClickListener = object : MySportsAdapter.OnItemClickListener{
                    override fun OnItemClick(view: View, position: Int) {
                        binding.apply {
                            // 운동리스트로 intent
                            sIdEdit.setText(adapter.getItem(position).sId.toString())
                            sNameEdit.setText(adapter.getItem(position).sName.toString())
                            sCountEdit.setText(adapter.getItem(position).sCount.toString())
                            checkBox.isChecked = adapter.getItem(position).k
                        }
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }

            deletestn.setOnClickListener {
                initAdapter()
                rdb.child(sIdEdit.text.toString()).removeValue()
                clearInput()
            }

            updatestn.setOnClickListener {
                initAdapter()
                rdb.child(sIdEdit.text.toString())
                    .child("scount")
                    .setValue(sCountEdit.text.toString().toInt())
                clearInput()
            }

            exitsrn.setOnClickListener {
                var date =  LocalDate.now().toString()
                var rdbpath = "Exercise/" + date
                var num =0
                var rand =""
                rdb = FirebaseDatabase.getInstance().getReference(rdbpath)
                for(i :Int in 1..adapter.itemCount){
                    rand = String.format("%06d",adapter.getItem(num).sId)
                    var item = ExerciseData(rand.toInt(),adapter.getItem(num).sName,1,0,adapter.getItem(num).sCount,adapter.getItem(num).k)
                    rdb.child(rand).setValue(item)
                    num+=1
                }
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
            val option = FirebaseRecyclerOptions.Builder<Sports>()
                .setQuery(query, Sports::class.java)
                .build()
            adapter = MySportsAdapter(option)
            adapter.itemClickListener = object : MySportsAdapter.OnItemClickListener{
                override fun OnItemClick(view: View, position: Int) {
                    binding.apply {
                        // 운동리스트로 intent
                        sIdEdit.setText(adapter.getItem(position).sId.toString())
                        sNameEdit.setText(adapter.getItem(position).sName.toString())
                        sCountEdit.setText(adapter.getItem(position).sCount.toString())
                        checkBox.isChecked = adapter.getItem(position).k
                    }
                }
            }
            binding.recyclerView.adapter = adapter
            adapter.startListening()
        }
    }

    fun clearInput(){
        binding.apply {
            sIdEdit.text.clear()
            sNameEdit.text.clear()
            sCountEdit.text.clear()
            checkBox.isChecked = false
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