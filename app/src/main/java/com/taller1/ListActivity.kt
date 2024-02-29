package com.taller1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class ListActivity : AppCompatActivity() {

    var arreglo: MutableList<JSONObject> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        //pasar de la lista a la activity detalle
        val intentDetalle = Intent(applicationContext, DetalleActivity::class.java)

        val lista = findViewById<ListView>(R.id.listView)
        // val adaptador = ArrayAdapter(this,android.R.layout.simple_list_item_1,arreglo)


        // para llenar el arreglo con los datos del json
        val json = JSONObject((loadJSONFromAsset()))
        fillArrayJson(json)

        //hacer que el adaptador sea personalizado para ver solo el nombre del json object
        val adaptador =
            object : ArrayAdapter<JSONObject>(this, android.R.layout.simple_list_item_1, arreglo) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val itemView = convertView ?: LayoutInflater.from(context)
                        .inflate(android.R.layout.simple_list_item_1, parent, false)
                    val textView = itemView.findViewById<TextView>(android.R.id.text1)
                    // Assuming "nombre" is the attribute you want to display
                    val nombre = getItem(position)?.getString("nombre") ?: ""
                    textView.text = nombre
                    return itemView
                }
            }

        lista.adapter = adaptador

        // es el evento que se dispara cuando se selecciona un item de la lista
        listaOnClickBundle(lista, intentDetalle)


    }

    // se llena el arreglo con los datos del json filtrando por categoria
    private fun fillArrayJson(json: JSONObject) {
        val destinosJson = json.getJSONArray("destinos")
        val dataList = SharedData.dataList
        val filterString = intent.getStringExtra("categoria")

        if (filterString == "favoritos"){
            //Toast.makeText(applicationContext, dataList.lastIndex.toString(), Toast.LENGTH_LONG).show()
            for (elemento in dataList){
                for (j in 0 until destinosJson.length()){
                    val jsonObject = destinosJson.getJSONObject(j)
                    val nombre = jsonObject.getString("nombre")
                    if (nombre == elemento) {
                        arreglo.add(jsonObject)
                    }
                }
            }
        }
        else{
            for (i in 0 until destinosJson.length()) {
                val jsonObject = destinosJson.getJSONObject(i)
                val categoria = jsonObject.getString("categoria")

                if (filterString == categoria) {
                    arreglo.add(jsonObject)
                } else if (filterString == "Todas") {
                    arreglo.add(jsonObject)
                }


            }
        }
    }


    private fun listaOnClickBundle(lista: ListView, intentDetalle: Intent) {
        lista.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // se crea un bundle para enviar los datos del json object al detalle activity

                val bundle = Bundle()
                bundle.putInt("id", arreglo[position].getInt("id"))
                bundle.putString("nombre", arreglo[position].getString("nombre"))
                bundle.putString("pais", arreglo[position].getString("pais"))
                bundle.putString("categoria", arreglo[position].getString("categoria"))
                bundle.putString("plan", arreglo[position].getString("plan"))
                bundle.putInt("precio", arreglo[position].getInt("precio"))


                intentDetalle.putExtra("msj", bundle)
                startActivity(intentDetalle)

            }
        })
    }

    // carga el json desde el archivo de recursos
    fun loadJSONFromAsset(): String? {
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