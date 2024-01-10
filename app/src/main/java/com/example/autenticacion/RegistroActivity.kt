package com.example.autenticacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.autenticacion.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = FirebaseFirestore.getInstance()
        title = "Nuevo usuario"

        //se hace click en el botón de registrar
        binding.registro.setOnClickListener {
            //se comprueba si hay algún campo vacío
            if (binding.nombre.text.isNotEmpty() && binding.apellidos.text.isNotEmpty() && binding.email.text.isNotEmpty() && binding.constrasenna.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.email.text.toString(),
                    binding.constrasenna.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        //crea un usuario en la tabla usuarios
                        db.collection("usuarios").document(binding.email.text.toString()).set(mapOf("nombre" to binding.nombre.text.toString(), "apellidos" to binding.apellidos.text.toString()))

                        val intent = Intent(this, Bienvenida::class.java).apply {
                            //variable que se pasará a la otra actividad
                            putExtra("nombreUsuario", binding.nombre.text.toString())
                        }
                        //se pasa a la actividad de bienvenida
                        startActivity(intent)
                    }
                    //si no se puede registrar el usuario, se dará un aviso
                    else { Toast.makeText(this, "Fallo en el registro de usuario", Toast.LENGTH_LONG).show() }
                }
            }
            //si al menos uno está vacío, dará un aviso
            else {
                Toast.makeText(this, "Hay un campo vacío", Toast.LENGTH_LONG).show()
            }
        }
    }
}