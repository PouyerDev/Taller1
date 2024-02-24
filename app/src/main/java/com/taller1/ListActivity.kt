package com.taller1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class ListActivity : AppCompatActivity() {

    var arreglo: MutableList<String> =ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)


        val intentDetalle = Intent(applicationContext,DetalleActivity::class.java)



        val lista= findViewById<ListView>(R.id.listView)
        val adaptador = ArrayAdapter(this,android.R.layout.simple_list_item_1,arreglo)
        lista.adapter = adaptador

        val json = JSONObject ((loadJSONFromAsset()))
        val paisesJson = json.getJSONArray("destinos")
        for (i in 0 until paisesJson.length()){
            val jsonObject = paisesJson.getJSONObject(i)
            arreglo.add(jsonObject.getString("nombre"))

        }

        lista.setOnItemClickListener(object: AdapterView.OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                intentDetalle.putExtra("msj",arreglo[position])
                startActivity(intentDetalle)

                Toast.makeText(baseContext,position.toString(), Toast.LENGTH_SHORT).show()

            }
        })







    }

    fun loadJSONFromAsset(): String? {
        var json: String? = null
        try {
            val `is`: InputStream = assets.open("destinos.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String (buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}