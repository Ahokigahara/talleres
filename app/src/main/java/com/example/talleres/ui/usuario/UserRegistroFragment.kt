package com.example.talleres.ui.usuario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.talleres.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.android.awaitFrame


class UserRegistroFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    var mProfileRef = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_registro, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_signin3).setOnClickListener {
            validarVacios(view)
        }

    }

    private fun validarVacios(view: View){
        var flag = false;
        var usuario: TextView =view.findViewById<TextView>(R.id.correo)
        var nombre: TextView =view.findViewById<TextView>(R.id.nombre)
        var direccion: TextView =view.findViewById<TextView>(R.id.direccion)
        var barrio: TextView =view.findViewById<TextView>(R.id.barrio)
        var pass : TextView = view.findViewById<TextView>(R.id.pass)

        var modelo: TextView =view.findViewById<TextView>(R.id.modelo)
        var marca: TextView =view.findViewById<TextView>(R.id.marca)
        var cilindraje: TextView =view.findViewById<TextView>(R.id.cilindraje)


        if( usuario.text.isNullOrEmpty()){
            usuario.setError("Campo vacío")
            usuario.requestFocus()

        }else if(nombre.text.isNullOrEmpty()){
            nombre.setError("Campo vacío")
            nombre.requestFocus()

        }else if(direccion.text.isNullOrEmpty()){
            direccion.setError("Campo vacío")
            direccion.requestFocus()
        }else if(barrio.text.isNullOrEmpty()){
            barrio.setError("Campo vacío")
            barrio.requestFocus()

        }else if(pass.text.isNullOrEmpty()){
            pass.setError("Campo vacío")
            pass.requestFocus()
        }else if(modelo.text.isNullOrEmpty()){
            modelo.setError("Campo vacío")
            modelo.requestFocus()

        }else if(marca.text.isNullOrEmpty()){
            marca.setError("Campo vacío")
            marca.requestFocus()
        }else if(cilindraje.text.isNullOrEmpty()){
            cilindraje.setError("Campo vacío")
            cilindraje.requestFocus()
        }else{
            var user = Usuario(usuario.text.toString(),nombre.text.toString(),direccion.text.toString(),barrio.text.toString(),pass.text.toString())
            var moto = Moto(usuario.text.toString(),Integer.parseInt(modelo.text.toString()),marca.text.toString(),Integer.parseInt(cilindraje.text.toString()))
            validarDatos(user, moto)

        }
    }

    private fun validarDatos(user:Usuario, moto: Moto){
        var flag =false;
        var aux =mProfileRef.child("usuarios").orderByChild("correo").equalTo(user.correo).addListenerForSingleValueEvent(object  : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(!p0.exists()) {
                    mProfileRef.child("usuarios").push().setValue(user)
                    mProfileRef.child("motos").push().setValue(moto)
                    Toast.makeText(this@UserRegistroFragment.requireContext(),"Registro Creado Correctamente",Toast.LENGTH_LONG).show()


                    mProfileRef.removeEventListener(this)
                    findNavController().navigate(R.id.nav_home)
                }else{
                    var pass : TextView = this@UserRegistroFragment.requireView().findViewById(R.id.correo)
                    pass.setError("Correo ya registrado")
                    Toast.makeText(this@UserRegistroFragment.requireContext(),"Usuario ya existe",Toast.LENGTH_LONG).show()
                }


            }

        })

    }
}
