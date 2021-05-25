package com.example.teamunderdog

data class ExerciseRecordData(var eId:Int, var eTitle:String, var eRecord:String, var eDate:String) {
    constructor():this(-1,"","","")
}