package com.example.teamunderdog

// id, 이름, 개수(false) or 시간(true), 양(count)
data class Sports(var sId:Int, var sName:String, var k:Boolean, var sCount:Int) {
    constructor():this(0, "noinfo", true, 0)
}