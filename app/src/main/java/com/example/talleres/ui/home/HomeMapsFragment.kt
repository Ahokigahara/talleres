package com.example.talleres.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.DateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.talleres.MainActivity
import com.example.talleres.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.*


class HomeMapsFragment : Fragment(), GoogleMap.OnMarkerClickListener {

    private lateinit var homeMapsViewModel: HomeMapsViewModel
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private  val MY_PERMISSIONS_REQUEST_FINE_LOCATION =1

    private val myMarker: Marker? = null
    lateinit var  mChildEventListener: ChildEventListener
    var mProfileRef = FirebaseDatabase.getInstance().reference


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        fusedLocationClient= LocationServices.getFusedLocationProviderClient(this.requireActivity())
        if (ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_FINE_LOCATION)
        }else{
            Toast.makeText(this.requireContext(), "permisos concedidos", Toast.LENGTH_SHORT).show();

        }
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    private val callback = OnMapReadyCallback { mMap ->
        /**
         * Funcionalidades del mapa que se activan una vez se hayan cargado todos los recursos
         */


        mMap.isMyLocationEnabled = true;
        mMap.setOnMarkerClickListener(this);
        mMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                this.context, R.raw.formato_mapa
            )
        );
        addMarkersToMap(mMap);


    }


    private fun addMarkersToMap(mMap: GoogleMap) {

        mMap.addMarker(
            MarkerOptions().position(LatLng(-33.852, 72.211)).title("Marker de prueba")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)))
        mProfileRef.child("talleres").addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p1: DataSnapshot) {
                for(snap:DataSnapshot in p1.children){

                    val fm : FirebaseMarker? = snap.getValue(FirebaseMarker::class.java)
                    if (fm != null) {
                        mMap.addMarker(
                            MarkerOptions().position(LatLng(fm.latitud,fm.longitud)).title(fm.nombre).snippet(fm.toString())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                        )
                    }
                }


            }

        })
        }

    override fun onMarkerClick(marker: Marker?): Boolean {

        val bundle = Bundle()
        bundle.putString("titulo taller", marker!!.title)
        bundle.putString("informacion", marker!!.snippet)

        val navController = this.view?.findNavController()

        navController?.navigate(R.id.infomarker,bundle)
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this.requireContext(), "permisos concedidos", Toast.LENGTH_SHORT).show()
                    callback.onMapReady(this.mMap)
                } else {
                    Toast.makeText(this.requireContext(), "No cuento con permisos para acceder a tu ubicacion", Toast.LENGTH_LONG).show()
                }
                return
            }


        }
    }
}

