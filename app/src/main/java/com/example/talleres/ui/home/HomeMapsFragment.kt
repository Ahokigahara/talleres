package com.example.talleres.ui.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.talleres.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.*


class HomeMapsFragment : Fragment(), GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private  val MY_PERMISSIONS_REQUEST_FINE_LOCATION =2

    private val myMarker: Marker? = null
    lateinit var  mChildEventListener: ChildEventListener
    var mProfileRef = FirebaseDatabase.getInstance().reference

    var flag=false

    var distMax = 5000


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        arguments?.let{
            //Without SafeArgs
            flag = getArguments()?.getBoolean("flag")!!

            }
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    private val callback = OnMapReadyCallback { mMap ->
        /**
         * Funcionalidades del mapa que se activan una vez se hayan cargado todos los recursos
         */
        if(isGPSProvider(this.requireContext()) && isNetowrkProvider(this.requireContext()) && checkPermissions()){

        mMap.isMyLocationEnabled=true
        }

            mMap.setOnMarkerClickListener(this);
            mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this.context, R.raw.formato_mapa
                )
            )

            addMarkersToMap(mMap);





    }


    private fun addMarkersToMap(mMap: GoogleMap) {
        fusedLocationClient= LocationServices.getFusedLocationProviderClient(this.requireActivity())
        fusedLocationClient.lastLocation.addOnSuccessListener{location: Location ->

           var pos=LatLng(location.latitude,location.longitude)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(pos, 17f)
        mMap.animateCamera(cameraUpdate)
        }


        val event = mProfileRef.child("talleres").addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p1: DataSnapshot) {
                for(snap:DataSnapshot in p1.children){
                    val fm : FirebaseMarker? = snap.getValue(FirebaseMarker::class.java)
                    if (fm != null) {
                        //Location to compare
                        var pos :LatLng =LatLng(fm.latitud,fm.longitud)
                        var targetLocation:Location = Location("")

                        targetLocation.setLongitude(pos.longitude);
                        targetLocation.setLatitude(pos.latitude);

                        fusedLocationClient= LocationServices.getFusedLocationProviderClient(this@HomeMapsFragment.requireActivity())
                        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location ->
                            var distancia = location.distanceTo(targetLocation)

                            if(distancia<=distMax) {
                                mMap.addMarker(
                                    MarkerOptions().position(pos).title(fm.nombre)
                                        .snippet(fm.toString())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                                )
                            }
                        }


                    }
                }
            }
        })

        mProfileRef.child("comentarios").removeEventListener(event)
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

    fun isGPSProvider(context: Context): Boolean {
        val lm =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun isNetowrkProvider(context: Context): Boolean {
        val lm =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun checkPermissions():Boolean {
        if (ContextCompat.checkSelfPermission(this@HomeMapsFragment.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            return false
        }else  if (ContextCompat.checkSelfPermission(this@HomeMapsFragment.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
           return true
        }
        return false
    }

}

