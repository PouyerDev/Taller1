package com.taller1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn1 = findViewById<Button>(R.id.button1)
        val btn2 = findViewById<Button>(R.id.button2)
        val btn3 = findViewById<Button>(R.id.button3)
        val spinner = findViewById<Spinner>(R.id.spinner)


        // Create an ArrayAdapter
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.categoria)
        )

        // Set the ArrayAdapter to the Spinner
        spinner.adapter = adapter

        // Set click listener for the Button
        btn1.setOnClickListener {
            // Get the selected item from the Spinner
            val selectedItem = spinner.selectedItem.toString()

            // Create an Intent to launch the next activity
            val intentList = Intent(applicationContext, ListActivity::class.java)
            // Put the selected item as an extra in the Intent
            intentList.putExtra("categoria", selectedItem)

            // Start the activity with the Intent
            startActivity(intentList)
        }
    }
}