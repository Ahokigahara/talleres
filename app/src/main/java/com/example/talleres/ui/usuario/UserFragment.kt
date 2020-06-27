package com.example.talleres.ui.usuario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.talleres.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.system.exitProcess


class UserFragment : Fragment() {


    var mProfileRef = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_iniciar_sesion, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_registrarse).setOnClickListener {
            findNavController().navigate(R.id.nav_registroUsuario)
        }

        view.findViewById<Button>(R.id.btn_signin).setOnClickListener {
            var usuario: TextView =view.findViewById<TextView>(R.id.usuario)
            var pass : TextView = view.findViewById<TextView>(R.id.contraseña)
           if( usuario.text.isNullOrEmpty()){
                   usuario.setError("Campo vacío")
               usuario.requestFocus()
           }else if(pass.text.isNullOrBlank()){
                pass.setError("Campo vacío")
                pass.requestFocus()
            }else{
            validarDatos(usuario.text.toString(),pass.text.toString())

           }
        }
    }

    private fun validarDatos(usuario : String, pass : String) {
        var userObj = Usuario(usuario,pass)
        var flag =false
        mProfileRef.child("usuarios").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var flag=true
                loop@ for (us in snapshot.children)  {
                    val usernameTaken =us.child("correo").getValue(
                            String::class.java
                        )
                    val passTaken =us.child("pass").getValue(
                        String::class.java
                    )
                    if (usuario.equals(usernameTaken!!)) {

                        if(pass.equals(passTaken!!)){

                            Toast.makeText(this@UserFragment.requireContext(),"Exito",Toast.LENGTH_LONG).show()
                            findNavController().navigate(R.id.nav_home)
                            break@loop
                        }else{
                            Toast.makeText(this@UserFragment.requireContext(),"Contraseña incorrecta",Toast.LENGTH_LONG).show()
                            break@loop
                        }
                    }else{
                        flag=false
                    }
                }
                if(flag) {
                    Toast.makeText(
                        this@UserFragment.requireContext(),
                        "Usuario incorrecto",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}
