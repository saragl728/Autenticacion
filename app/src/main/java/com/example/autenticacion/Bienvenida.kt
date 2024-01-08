package com.example.autenticacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.autenticacion.databinding.ActivityBienvenidaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Bienvenida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBienvenidaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Bienvenida"
        val extras = intent.extras

        val db = FirebaseFirestore.getInstance()    //necesario para trabajar con la base de datos

        //se le pasa el nombre de usuario que se puso en la actividad anterior
        val nombre = extras?.getString("nombreUsuario")

        //el saludo incluirá el nombre de usuario
        binding.saludo.text = "Bienvenido/a, $nombre"

        //guardar coche
        binding.bAnyadirCoche.setOnClickListener {
            //se comprueba que no hay campos en blanco
            if (binding.marca.text.isNotEmpty() && binding.modelo.text.isNotEmpty() && binding.matricula.text.isNotEmpty() && binding.color.text.isNotEmpty()) {
                db.collection("coches").add(
                    mapOf(
                        "color" to binding.color.text.toString(),
                        "marca" to binding.marca.text.toString(),
                        "matricula" to binding.matricula.text.toString(),
                        "modelo" to binding.modelo.text.toString()
                    )
                )
                    //se añade un mensaje si ha funcionado
                    .addOnSuccessListener { documento ->
                        Toast.makeText(this, "Nuevo coche añadido con el id: ${documento.id}", Toast.LENGTH_LONG).show()
                    }
                    //se añade un mensaje si ha habido un error
                    .addOnFailureListener {
                        Toast.makeText(this, "Error añadiendo el coche", Toast.LENGTH_LONG).show()
                    }

            } else {
                //si un campo está vacío, muestra un toast
                Toast.makeText(this, "Algún campo está vacío", Toast.LENGTH_LONG).show()
            }
        }


        //cierre de sesión
        binding.cierreSesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            //se vuelve al inicio después de cerrar sesión
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}