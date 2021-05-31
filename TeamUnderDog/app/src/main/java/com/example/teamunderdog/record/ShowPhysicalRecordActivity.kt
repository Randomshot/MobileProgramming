package com.example.teamunderdog.record

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.teamunderdog.R
import com.example.teamunderdog.databinding.ActivityShowPhysicalRecordBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject


class ShowPhysicalRecordActivity : AppCompatActivity() {
    lateinit var binding:ActivityShowPhysicalRecordBinding
    lateinit var rdb: DatabaseReference
    private var lineChart: LineChart? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowPhysicalRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initChart("몸무게(kg)")
        init()
    }

    fun initChart(kind:String){
        lineChart = null
        lineChart = findViewById(R.id.chart)
        var entries: ArrayList<Entry> = ArrayList()
        binding.goAddPhysicalRecordBtn.setOnClickListener {
            val intent = Intent(this,AddPhysicalRecordActivity::class.java)
            startActivity(intent)
        }
        val rdbpath = "PhysicalRecord/"
        rdb = FirebaseDatabase.getInstance().getReference(rdbpath)
        val query = rdb.limitToFirst(100)

        rdb.child("/").get().addOnSuccessListener {


            val json = JSONObject(it.value.toString())
            val keyList = json.keys()
            for( i in keyList){
                val data = json.getJSONObject(i)
                val dataKey = data.keys()
                for(j in dataKey){
                    val recordJson = data.getJSONObject(j)
                    val recordDate = recordJson.getString("date")
                    val recordKind = recordJson.getString("kind")
                    val recordValue = recordJson.getString("value")
                    if(recordKind == kind){
                        var dateArr = recordDate.split("-")
                        entries.add(Entry(recordDate.replace("-","").replace("2021","").toFloat(),recordValue.toString().toFloat()))

                    }
                }
            }
            var dataset: LineDataSet = LineDataSet(entries, kind)
            dataset.apply {
                lineWidth = 2.0f
                circleRadius = 3.0f
                valueTextSize = 13.0f
            }
            var linedata: LineData = LineData(dataset)
            lineChart?.data = linedata
            lineChart?.apply {

                moveViewToX(linedata.entryCount.toFloat())
                axisRight?.isEnabled =false
                xAxis?.position = XAxis.XAxisPosition.BOTTOM
                xAxis?.setValueFormatter { value, axis ->
                    val month = (value.toInt()/100).toString()
                    var day:String=""
                    if((value.toInt()%100)>0.5) day = "15"
                    else day = "1"
                    val result:String = month + "/" + day
                    result
                }
                xAxis?.textSize = 15f
                axisLeft?.textSize = 15f
                animateXY(1, 1)

            }


        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
    private fun init() {
        var kind:String?=null
        var spinnerdataList = arrayOf("몸무게(kg)","체지방률(%)","골격근량(kg)")
        var spinnerdata = listOf<String>("몸무게(kg)","체지방률(%)","골격근량(kg)")
        var adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,spinnerdata)

        binding.showPhysicalRecordChartSpinner.adapter = adapter
        binding.showPhysicalRecordChartSpinner.setSelection(0)
        binding.showPhysicalRecordChartSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    kind = spinnerdataList[position]
                    initChart(kind!!)

            }

        }
    }
}

