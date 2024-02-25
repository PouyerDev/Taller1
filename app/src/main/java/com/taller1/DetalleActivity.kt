package com.taller1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DetalleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        val text1 = findViewById<TextView>(R.id.textView)
        val text2 = findViewById<TextView>(R.id.textView2)
        val text3 = findViewById<TextView>(R.id.textView3)
        val text4 = findViewById<TextView>(R.id.textView4)
        val text5 = findViewById<TextView>(R.id.textView5)

        //recibir datos de listActivity y mostrarlos en la activity detalle

        val infoRecivida = intent.getBundleExtra("msj")
        text1.text = infoRecivida?.getString("nombre")
        text2.text = infoRecivida?.getString("pais")
        text3.text = infoRecivida?.getString("categoria")
        text4.text = infoRecivida?.getString("plan")
        text5.text = infoRecivida?.getInt("precio").toString()


    }
}