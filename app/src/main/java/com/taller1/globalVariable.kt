package com.taller1

class SharedData {
    companion object {
        val dataList: ArrayList<String> by lazy { ArrayList<String>() }
        val categoryList: ArrayList<String> by lazy { ArrayList<String>() }
    }
}