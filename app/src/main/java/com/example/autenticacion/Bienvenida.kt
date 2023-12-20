package com.example.autenticacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.autenticacion.databinding.ActivityBienvenidaBinding
import com.google.firebase.auth.FirebaseAuth

class Bienvenida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBienvenidaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Bienvenida"
        val extras = intent.extras

        //se le pasa el nombre de usuario que se puso en la actividad anterior
        val nombre = extras?.getString("nombreUsuario")

        //el saludo incluirá el nombre de usuario
        binding.saludo.text = "Bienvenido/a, $nombre"

        //cierre de sesión
        binding.cierreSesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            //se vuelve al inicio después de cerrar sesión
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}