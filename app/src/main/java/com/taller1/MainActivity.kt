package com.taller1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn1 =findViewById<Button>(R.id.button1)
        val btn2 =findViewById<Button>(R.id.button2)
        val btn3 =findViewById<Button>(R.id.button3)
        val spinner =findViewById<Spinner>(R.id.spinner)


        //boton para ir a la activity list explorar destinos
        val intentList = Intent(applicationContext,ListActivity::class.java)
        btn1.setOnClickListener {
            startActivity(intentList)
        }











    }
}