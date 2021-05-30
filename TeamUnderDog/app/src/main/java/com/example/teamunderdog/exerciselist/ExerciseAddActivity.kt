package com.example.teamunderdog.exerciselist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teamunderdog.databinding.ActivityExerciseAddBinding

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class ExerciseAddActivity : AppCompatActivity() {
    lateinit var binding: ActivityExerciseAddBinding
    lateinit var rdb: DatabaseReference
    var date:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun editTextClear(){
        binding.apply {
            addExerciseTitleEditText.text.clear()
            addExerciseSetsNumEditText.text.clear()
            addExerciseWeightEditText.text.clear()
            addExerciseCountEditText.text.clear()
        }
    }
    private fun init() {


        date = intent.getStringExtra("date")
        val rdbpath = "Exercise/" + date
        rdb = FirebaseDatabase.getInstance().getReference(rdbpath)

        binding.addExerciseCompnentBtn.setOnClickListener {
            val title = binding.addExerciseTitleEditText.text.toString()
            val setNum = binding.addExerciseSetsNumEditText.text.toString().toInt()
            val weight = binding.addExerciseWeightEditText.text.toString().toInt()
            val count = binding.addExerciseCountEditText.text.toString().toInt()
            val randNum = Random().nextInt(999999)
            val values = ExerciseData(randNum, title, setNum, weight, count)
            rdb.child(randNum.toString()).setValue(values)
            editTextClear()
        }
        binding.addExerciseComponentCancelBtn.setOnClickListener {
            editTextClear()
        }

    }



}