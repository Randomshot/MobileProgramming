package com.example.teamunderdog.record

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.teamunderdog.R
import com.example.teamunderdog.databinding.ActivityAddPhysicalRecordBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate

class AddPhysicalRecordActivity : AppCompatActivity() {

    lateinit var binding:ActivityAddPhysicalRecordBinding
    lateinit var rdb: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPhysicalRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {


        var kind:String?=null
        var date = LocalDate.now().toString()
        val rdbpath = "PhysicalRecord/"
        var dataList = arrayOf("몸무게(kg)","체지방률(%)","골격근량(kg)")
        var data = listOf<String>("선택하세요","몸무게(kg)","체지방률(%)","골격근량(kg)")
        var adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,data)
        rdb = FirebaseDatabase.getInstance().getReference(rdbpath)
        binding.AddPhysicalRecordSpinner.adapter = adapter
        binding.AddPhysicalRecordSpinner.setSelection(0)
        binding.AddPhysicalRecordSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position != 0){

                    kind = dataList[position-1]
                    binding.AddPhysicalRecordSubmitBtn.isEnabled = true
                }
            }

        }
        binding.AddPhysicalRecordDatePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            if(monthOfYear>=9){
                date = year.toString()+"-"+(monthOfYear+1).toString()+"-"+dayOfMonth.toString()
            }
            else{
                date = year.toString()+"-0"+(monthOfYear+1).toString()+"-"+dayOfMonth.toString()
            }
        }
        binding.AddPhysicalRecordSubmitBtn.setOnClickListener {
            val value = binding.AddPhysicalRecordValueEditText.text.toString().toInt()

            val values = PhysicalData(kind!!,value,date)
            rdb.child(date+"/"+kind!!).setValue(values)
            binding.AddPhysicalRecordSpinner.setSelection(0)
            binding.AddPhysicalRecordValueEditText.text.clear()
        }
    }
}