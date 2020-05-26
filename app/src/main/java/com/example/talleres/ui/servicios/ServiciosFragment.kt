package com.example.talleres.ui.servicios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ExpandableListView
import androidx.fragment.app.Fragment
import com.example.talleres.R
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*


class ServiciosFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel
    private var grua : MutableList<Servicio> = TODO();
    private var repuestos : MutableList<Servicio> = TODO();
    private var cdd : MutableList<Servicio> = TODO();
    private var soat : MutableList<Servicio> = TODO();
    private var accesorios : MutableList<Servicio> = TODO();
    private var headers:MutableList<String>;
    private var datos: HashMap<String,MutableList<Servicio>>;

    lateinit var  mChildEventListener: ChildEventListener
    var mProfileRef = FirebaseDatabase.getInstance().reference


    var listAdapter: ExpandableListAdapter? = null
    var expListView: ExpandableListView? = null
    var listDataHeader: List<String>? = null
    var listDataChild: HashMap<String, List<String>>? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        expListView= view?.findViewById<ExpandableListView>(R.id.lvExp)
        obtenerRegistros()
        listAdapter= ExpandableListAdapter(this.context,listDataHeader,listDataChild)
        expListView?.setAdapter(listAdapter)
       // obtenerRegistros()
        return inflater.inflate(R.layout.fragment_listview, container, false)
    }


    /*
    (GRUA 1
REPUESTOS 2
CENTRO DE DIAGNOSTICO 3
SOAT 4
ACCESORIOS 5)
     */
    fun obtenerRegistros(){



        mProfileRef.child("servicios").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p1: DataSnapshot) {
                for(snap: DataSnapshot in p1.children){

                    val fm : Servicio? = snap.getValue(Servicio::class.java)
                    if (fm != null) {
                        when(fm.grupo){
                            1 -> grua.add(fm);
                            2 -> repuestos.add(fm);
                            3 -> cdd.add(fm);
                            4 -> soat.add(fm);
                            5 -> accesorios.add(fm);
                        }
                    }
                }
            }

        })
        datos.put("GRUA",grua)
        datos.put("REPUESTOS",repuestos)
        datos.put("CENTRO DE DIAGNÓSTICO",cdd)
        datos.put("SOAT",soat)
        datos.put("ACCESORIOS",accesorios)
    }
}
