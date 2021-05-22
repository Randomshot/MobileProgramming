package com.example.teamunderdog

data class ExerciseData(var eTitle:String, var eSetsNum:Int, var eWeight:Int, var eCount:Int) {
    constructor():this("",0,0,0)
}