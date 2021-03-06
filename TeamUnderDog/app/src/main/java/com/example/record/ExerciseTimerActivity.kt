package com.example.teamunderdog.record

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.teamunderdog.databinding.ActivityExerciseTimerBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.concurrent.timer

class ExerciseTimerActivity : AppCompatActivity()  {
    lateinit var binding: ActivityExerciseTimerBinding
    lateinit var rdb: DatabaseReference
    private var time = 0
    private var timeRest = 0
    private var timeNum = 0
    private var isRunning = false
    private var isRestNow = false
    private var timerTaskTotal: Timer? = null
    private var timerTaskRest: Timer? = null
    var flag = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onStop() {
        super.onStop()
        timerTaskTotal?.cancel()
        timerTaskRest?.cancel()
    }

    private fun startTotal(){
        timerTaskTotal = timer(period = 10){
            time++
        }
    }
    private fun startRest(num:Int,count:Int){
        timeRest = num
        timerTaskRest = timer(period = 10){
            timeRest--
            var sec = timeRest / 100
            //var sec = timeRest / 100
            //var milli = timeRest % 100
            if(sec == 0 /*&& milli == 0*/) {
                reset(timerTaskRest,num,count)
            }
            else{
                runOnUiThread{
                    binding.secTextView.text = "$sec"
                    //binding.milliTextView.text = "$milli"
                }
            }
        }
    }

    private fun pauseTotal(timer: Timer?){
        timer?.cancel()
    }
    private fun reset(timer: Timer?,num: Int,count:Int){
        timer?.cancel()
        timeRest = num
        isRestNow = false
        binding.secTextView.text = (num/100).toString()
        //binding.milliTextView.text = "00"
    }
    private fun init() {
        val date = intent.getStringExtra("date")
        val name = intent.getStringExtra("name")
        val setNumber = intent.getIntExtra("set",1)
        val countNumber = intent.getIntExtra("count",1)
        val id = intent.getIntExtra("id",1)
        val k = intent.getBooleanExtra("k",true)
        //var set = setNumber-1
        var count = 1
        if(k==true){
            count = setNumber
            timeNum = 3000
            binding.secTextView.text = "30"
        }
        else{
            timeNum = countNumber*100
            binding.secTextView.text = countNumber.toString()
        }
        binding.apply {
            countNum.text = "?????? ?????? ???: "+ count.toString()
            //setNum.text = "?????? ?????????: "+set.toString()

            startBtn.setOnClickListener {
                flag = true
                if(isRunning == false){
                    isRunning = true
                    Toast.makeText(this@ExerciseTimerActivity,"????????? ???????????????.",Toast.LENGTH_SHORT).show()
                    startTotal()
                    startBtn.setText("?????? ??????")
                }
                else{
                    isRunning = false
                    pauseTotal(timerTaskTotal)
                    Toast.makeText(this@ExerciseTimerActivity,"????????? ?????? ???????????????.",Toast.LENGTH_SHORT).show()
                    startBtn.setText("????????????")
                }

            }
            restBtn.setOnClickListener {
                if(flag == false) {
                    Toast.makeText(this@ExerciseTimerActivity,"?????? ?????? ????????? ???????????????.",Toast.LENGTH_SHORT).show()
                }
                else{
                    if(count == 0 && isRestNow == false)
                    {
                        isRestNow = true
                        count --
                        Toast.makeText(this@ExerciseTimerActivity,"????????? ???????????????.",Toast.LENGTH_SHORT).show()
                        pauseTotal(timerTaskTotal)
                        val recodTime : Int = (time/100)
                        //val recodTime :String = (time / 6000).toString() + "??? "+((time %6000)/100).toString()+"???"
                        val rdbpath = "MyRecord/"+date.toString()
                        rdb = FirebaseDatabase.getInstance().getReference(rdbpath)
                        val values = ExerciseRecordData(id, name.toString(), recodTime, date.toString())
                        rdb.child(id.toString()).setValue(values)
                    }

                    else if(count > 0 && isRestNow == false){
                        isRestNow = true
                        startRest(timeNum,count)
                        count --
                        countNum.text = "?????? ?????? ???: "+count.toString()
                        if(count == 0){
                            restBtn.setText("?????? ??????")
                        }
                    else if (count < 0){
                        Toast.makeText(this@ExerciseTimerActivity,"?????? ????????? ?????????????????????.",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@ExerciseTimerActivity,"????????? ?????????.",Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }
    }
}}
