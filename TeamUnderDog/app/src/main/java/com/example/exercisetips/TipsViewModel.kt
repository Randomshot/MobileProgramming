package com.example.exercisetips

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TipsViewModel : ViewModel() {

    val selectednum = MutableLiveData<Int>()

    fun setLiveData(num:Int){
        selectednum.value = num
    }
}