package com.example.talleres.ui.infoMarker

import android.app.Dialog
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.talleres.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.content_info_window_marker.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class InfoWindowFragment : Fragment() {
    lateinit var comentarios: ArrayList<Comentario>

    lateinit var listView: ListView
    lateinit var listAdapter: ListAdapter

    private var listDataChild: ArrayList<Comentario> = ArrayList()

    var mProfileRef = FirebaseDatabase.getInstance().reference
    lateinit var mChildEventListener: ChildEventListener

    lateinit var titulo : String
    lateinit var info : String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.content_info_window_marker, container, false)

    }


   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       view.findViewById<Button>(R.id.btn_adicionar).setOnClickListener {
showDialog(titulo)
       }

        listView= view.findViewById(R.id.lvcomments)
        arguments?.let{
            //Without SafeArgs
             titulo = getArguments()?.getString("titulo taller")!!;
             info = getArguments()?.getString("informacion")!!;
            var valores: List<String>
            if (info != null) {
                valores =info.split(",")
                direccion.setText(valores.get(0))
                telefono.setText(valores.get(1))
            }
            titulo_taller.setText(titulo)
            if (titulo != null) {
                leerDatosBD(titulo)
            }

        }
    }





    fun leerDatosBD(taller: String) {
        comentarios = ArrayList()
        mProfileRef.child("comentarios").addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(p1: DataSnapshot) {
                for (snap: DataSnapshot in p1.children) {
                    val fm: Comentario? = snap.getValue(Comentario::class.java)
                    //filtrar por taller

                    if (fm != null && fm.getNombreTaller().equals(taller)) {

                        comentarios.add(fm);

                    }
                }
                listAdapter= ListAdapter(requireContext(),comentarios)
                listView!!.setAdapter(listAdapter)
            }
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }



    private fun showDialog(title: String) {
        val dialog = Dialog(this.requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_comentario)
        val usuario :TextView = dialog.findViewById(R.id.txtUsuario)
        val comentario : TextView = dialog.findViewById(R.id.txtComentario)

        val yesBtn = dialog.findViewById(R.id.btn_comentar) as Button
        val noBtn = dialog.findViewById(R.id.btn_cancelar) as Button
        yesBtn.setOnClickListener {
            var sComment = comentario.text.toString()
                var sUser = usuario.text.toString()
            var newComentario = Comentario(sComment, title,
                sUser
            )
            mProfileRef.child("comentarios").push().setValue(newComentario)

            val bundle = Bundle()
            bundle.putString("titulo taller", titulo)
            bundle.putString("informacion", info)

            this.leerDatosBD(titulo)
            dialog.dismiss()
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }



}
