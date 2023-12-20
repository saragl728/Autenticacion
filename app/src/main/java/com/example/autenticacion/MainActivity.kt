package com.example.autenticacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.autenticacion.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //si se intenta iniciar sesión
        binding.inicioSesion.setOnClickListener {
            login()
        }
    }

    private fun login(){
        //comprobamos que los campos de email y contraseña no están vacíos
        if (binding.email.text.isNotEmpty() && binding.contrasenya.text.isNotEmpty()){
            //se inicia sesión con el método signIn y se envía a FireBase el correo y contraseña introducidos
            FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.email.text.toString(), binding.contrasenya.text.toString())
                .addOnCompleteListener {
                    //si la contraseña es correcta irá a la pantalla de bienvenida
                    if (it.isSuccessful) {
                        val intent = Intent(this, Bienvenida::class.java)
                        startActivity(intent)
                    }

                    //si la contraseña o el correo son incorrectos, se muestra al usuario
                    else {
                        Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_LONG).show()
                    }
                }
        }

        //si hay un campo vacío, se lo dice al usuario
        else {
            Toast.makeText( this, "Algún campo está vacío", Toast.LENGTH_LONG).show()
        }
    }
}