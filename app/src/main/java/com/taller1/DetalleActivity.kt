package com.taller1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.taller1.SharedData

class DetalleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        val text1 = findViewById<TextView>(R.id.textView)
        val text2 = findViewById<TextView>(R.id.textView2)
        val text3 = findViewById<TextView>(R.id.textView3)
        val text4 = findViewById<TextView>(R.id.textView4)
        val text5 = findViewById<TextView>(R.id.textView5)

        val button = findViewById<Button>(R.id.buttonFav)

        //recibir datos de listActivity y mostrarlos en la activity detalle

        val infoRecibida = intent.getBundleExtra("msj")
        text1.text = infoRecibida?.getString("nombre")
        text2.text = infoRecibida?.getString("pais")
        text3.text = infoRecibida?.getString("categoria")
        text4.text = infoRecibida?.getString("plan")
        text5.text = infoRecibida?.getInt("precio").toString()

        button.setOnClickListener {
            addFavorite(infoRecibida?.getString("nombre"), infoRecibida?.getString("categoria"))
            button.isEnabled = false
        }


    }

    private fun addFavorite (nombre: String?, category: String?){
        if(nombre != null && category != null){
            if(SharedData.dataList.contains(nombre)){
                Toast.makeText(applicationContext, "La ubicación ya se encuentra en favoritos", Toast.LENGTH_LONG).show()
            }
            else{
                SharedData.dataList.apply {
                    add(nombre)
                }
                SharedData.categoryList.apply {
                    add(category)
                }
                Toast.makeText(applicationContext, "Agregado a favoritos", Toast.LENGTH_LONG).show()

            }
        }

    }
}