package com.example.teamunderdog.routine

data class Routine(var rId:Int, var rName:String, var sportslist:MutableList<Sports>) {
    constructor():this(0, "noinfo", mutableListOf())
}