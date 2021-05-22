package com.example.teamunderdog

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.teamunderdog.databinding.ActivityExerciseListBinding
import java.time.LocalDate

class ExerciseListActivity : AppCompatActivity() {
    lateinit var binding: ActivityExerciseListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun setDataAtFragment(fragment: Fragment, date:String){
        //kinds : all, wrong ,favorites
        val bundle = Bundle()
        bundle.putString("date",date)
        fragment.arguments = bundle
        setFragment(fragment)

    }
    fun setFragment(fragment: Fragment){
        val transaction =supportFragmentManager.beginTransaction()
        transaction.replace(R.id.exerciseListFrameLayout,fragment)
        transaction.commit()
    }
    private fun init() {
        var date = LocalDate.now().toString()
        setDataAtFragment(ExerciseListFragment(),date)

        binding.datePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            date = year.toString()+"-"+monthOfYear.toString()+"-"+dayOfMonth.toString()
            setDataAtFragment(ExerciseListFragment(),date)
        }
        binding.addExerciseBtn.setOnClickListener {
            val intent = Intent(this, ExerciseAddActivity::class.java)
            intent.putExtra("date",date)
            startActivity(intent)
        }
    }
}