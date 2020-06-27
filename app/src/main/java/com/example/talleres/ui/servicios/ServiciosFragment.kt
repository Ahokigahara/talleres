package com.example.talleres.ui.servicios

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.fragment.app.Fragment
import com.example.talleres.R
import com.google.firebase.database.*


class ServiciosFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel;
    private var grua :ArrayList<Servicio> = ArrayList()
    private  var repuestos :ArrayList<Servicio> = ArrayList()
    private  var cdd :ArrayList<Servicio> = ArrayList()
    private  var soat :ArrayList<Servicio> = ArrayList()
    private  var accesorios :ArrayList<Servicio> = ArrayList()


    private  var listDataHeader:ArrayList<String> = ArrayList()
    private var listDataChild: HashMap<String,ArrayList<Servicio>> = HashMap()

    lateinit var  mChildEventListener: ChildEventListener
    var mProfileRef = FirebaseDatabase.getInstance().reference


    lateinit var listAdapter: ExpandableListAdapter
    lateinit var expListView: ExpandableListView


override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {

    return inflater.inflate(R.layout.fragment_listview, container, false)

}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expListView= view.findViewById(R.id.lvExp)

        obtenerRegistros()
        listAdapter= ExpandableListAdapter(this@ServiciosFragment.context,listDataHeader,listDataChild)
        expListView!!.setAdapter(listAdapter)


    }


    /*
    (GRUA 1
REPUESTOS 2
CENTRO DE DIAGNOSTICO 3
SOAT 4
ACCESORIOS 5)
     */



    fun obtenerRegistros(){
        listDataHeader = ArrayList()
        listDataChild = HashMap()

        listDataHeader.add("Grúa")
        listDataHeader.add("Repuestos")
        listDataHeader.add("Centro de diagnóstico")
        listDataHeader.add("SOAT")
        listDataHeader.add("Accesorios")
        leerDatos();
        listDataChild[listDataHeader[0]] = grua // Header, Child data
        listDataChild[listDataHeader[1]] = repuestos
        listDataChild[listDataHeader[2]] = cdd
        listDataChild[listDataHeader[3]] = soat
        listDataChild[listDataHeader[4]] = accesorios
    }

fun leerDatos(){
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
}


}
