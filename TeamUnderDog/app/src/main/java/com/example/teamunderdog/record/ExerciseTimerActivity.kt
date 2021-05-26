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
            var milli = timeRest % 100
            if(sec == 0 && milli == 0) {
                reset(timerTaskRest,num,count)
            }
            else{
                runOnUiThread{
                    binding.secTextView.text = "$sec"
                    binding.milliTextView.text = "$milli"
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
        if(count == 1){
            binding.secTextView.text = "150"
            binding.milliTextView.text = "00"
        }
        else{
            binding.secTextView.text = "30"
            binding.milliTextView.text = "00"
        }
    }
    private fun init() {
        val date = intent.getStringExtra("date")
        val name = intent.getStringExtra("name")
        val setNumber = intent.getIntExtra("set",1)
        val countNumber = intent.getIntExtra("count",1)
        val id = intent.getIntExtra("id",1)
        var set = setNumber
        var count = countNumber

        binding.apply {
            countNum.text = "남은 횟수: "+ count.toString()
            setNum.text = "남은 세트수: "+set.toString()
            startBtn.setOnClickListener {
                flag = true
                if(isRunning == false){
                    isRunning = true
                    Toast.makeText(this@ExerciseTimerActivity,"운동을 시작합니다.",Toast.LENGTH_SHORT).show()
                    startTotal()
                    startBtn.setText("운동 정지")
                }
                else{
                    isRunning = false
                    pauseTotal(timerTaskTotal)
                    Toast.makeText(this@ExerciseTimerActivity,"운동을 잠시 중단합니다.",Toast.LENGTH_SHORT).show()
                    startBtn.setText("운동재개")
                }

            }
            restBtn.setOnClickListener {
                if(flag == false) {
                    Toast.makeText(this@ExerciseTimerActivity,"운동 시작 버튼을 눌러주세요.",Toast.LENGTH_SHORT).show()
                } else{
                    if(set == 0 && count == 0 && isRestNow == false)
                    {
                        Toast.makeText(this@ExerciseTimerActivity,"운동기록을 시작합니다.",Toast.LENGTH_SHORT).show()
                        flag = false
                        isRestNow = true
                        pauseTotal(timerTaskTotal)
                        val recodTime :String = (time / 6000).toString() + "분 "+((time %6000)/100).toString()+"초"
                        val rdbpath = "MyRecord"
                        rdb = FirebaseDatabase.getInstance().getReference(rdbpath)
                        val values = ExerciseRecordData(id, name.toString(), recodTime, date.toString())
                        rdb.child(id.toString()).setValue(values)
                    }
                    else if(set >= 0 && isRestNow == false){
                        if(set != 0 && count == 0){
                            isRestNow = true
                            startRest(15000,count)
                            count = countNumber // CountNum 넣을 예정
                            set--
                            countNum.text = "남은 횟수: "+count.toString()
                            setNum.text = "남은 세트수: "+set.toString()

                        }
                        else{
                            isRestNow = true
                            startRest(3000,count)
                            count--
                            countNum.text = "남은 횟수: "+count.toString()
                            if(set == 0 && count == 0){
                                restBtn.setText("운동 기록")
                            }
                        }
                    }
                    else{
                        Toast.makeText(this@ExerciseTimerActivity,"휴식중입니다.",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}