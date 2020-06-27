package com.example.talleres.ui.info

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.fragment.app.Fragment
import com.example.talleres.R
import com.google.firebase.database.*


class InfoFragment : Fragment() {




override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {

    return inflater.inflate(R.layout.fragment_info, container, false)

}


}
