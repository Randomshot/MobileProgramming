package com.example.teamunderdog.exerciselist

data class ExerciseData(var eId:Int, var eTitle:String, var eSetsNum:Int, var eWeight:Int, var eCount:Int, var k:Boolean) {
    constructor():this(-1,"",0,0,0,true)
}