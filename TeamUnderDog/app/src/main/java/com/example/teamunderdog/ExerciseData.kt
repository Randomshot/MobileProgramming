package com.example.teamunderdog

data class ExerciseData(var eId:Int, var eTitle:String, var eSetsNum:Int, var eWeight:Int, var eCount:Int) {
    constructor():this(-1,"",0,0,0)
}