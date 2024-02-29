package com.taller1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

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
        btn2.setOnClickListener {
            favorites()
        }
        btn3.setOnClickListener{
            recommended()
        }
    }

    private fun favorites() {
        // Create an Intent to launch the next activity
        val intentList = Intent(applicationContext, ListActivity::class.java)
        intentList.putExtra("categoria", "favoritos")
        // Start the activity with the Intent
        startActivity(intentList)
    }

    private fun recommended() {
        val maxOccurring = SharedData.categoryList.groupBy { it }.mapValues { it.value.size }.maxBy { it.value }.key
        val cantidad = SharedData.categoryList.groupBy { it }.mapValues { it.value.size }.maxBy { it.value }.value - 1
        if (cantidad != -1){
            val rnds = (0..cantidad).random()
            createBundle(rnds, maxOccurring)
        }
        else{
            createBundle(1, "NA")
        }
    }

    private fun createBundle(posicion: Int, categoria: String) {
        val intentDetalle = Intent(applicationContext, DetalleActivity::class.java)
        val json = JSONObject(loadJSONFromAsset())
        val necesitado = fillArrayJson(json, categoria, posicion)
        val bundle = Bundle()
        bundle.putInt("id", necesitado[0].toInt())
        bundle.putString("nombre", necesitado[1])
        bundle.putString("pais", necesitado[2])
        bundle.putString("categoria", necesitado[3])
        bundle.putString("plan", necesitado[4])
        bundle.putInt("precio", necesitado[5].toInt())


        intentDetalle.putExtra("msj", bundle)
        startActivity(intentDetalle)
    }

    private fun fillArrayJson(json: JSONObject, categoria: String, posicion: Int): ArrayList<String> {
        var contador = 0
        val destinosJson = json.getJSONArray("destinos")
        var arreglo: ArrayList<String> = ArrayList()
        for (i in 0 until destinosJson.length()) {
            val jsonObject = destinosJson.getJSONObject(i)

            if (categoria == jsonObject.getString("categoria")) {
                if (contador == posicion){
                    arreglo += jsonObject.getInt("id").toString()
                    arreglo += jsonObject.getString("nombre")
                    arreglo += jsonObject.getString("pais")
                    arreglo += jsonObject.getString("categoria")
                    arreglo += jsonObject.getString("plan")
                    arreglo += jsonObject.getInt("precio").toString()
                    return arreglo
                }
                else{
                    contador++
                }
            }
        }
        arreglo += "0"
        arreglo += "NA"
        arreglo += "NA"
        arreglo += "NA"
        arreglo += "NA"
        arreglo += "0"
        return arreglo
    }

    private fun loadJSONFromAsset(): String? {
        var json: String? = null
        try {
            val `is`: InputStream = assets.open("destinos.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}