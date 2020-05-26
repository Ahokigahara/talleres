package com.example.talleres.ui.infoMarker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.talleres.R
import kotlinx.android.synthetic.main.content_info_window_marker.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class InfoWindowFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_info_window_marker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            //Without SafeArgs
            var titulo = getArguments()?.getString("titulo taller");
            var info = getArguments()?.getString("informacion");
            var valores: List<String>
            if (info != null) {
                valores =info.split(",")
                direccion.setText(valores.get(0))
                telefono.setText(valores.get(1))
            }
            titulo_taller.setText(titulo)



        }
    }
}
