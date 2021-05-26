package com.example.teamunderdog.record

data class ExerciseRecordData(var eId:Int, var eTitle:String, var eRecord:String, var eDate:String) {
    constructor():this(-1,"","","")
}