package com.example.talleres.ui.servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.talleres.R;

public class ExpandableListAdapter extends BaseExpandableListAdapter implements ExpandableListView.OnChildClickListener {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<Servicio>> _listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, ArrayList<Servicio>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        Servicio servicio = (Servicio) getChild(groupPosition, childPosition);
        final String childText = servicio.getNombre() + "\n" + servicio.getTelefono();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fragment_card, null);
            convertView.setOnClickListener(v -> {

                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:"+servicio.getTelefono()));
                    _context.startActivity(i);
                /*if (ActivityCompat.checkSelfPermission(_context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(_context, "click", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(_context, "click2", Toast.LENGTH_SHORT).show();
                }*/
            });
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (this._listDataChild.get(this._listDataHeader.get(groupPosition)) == null) {
            return 0;
        } else {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fragment_listheader, null);
        }
        ImageView img = (ImageView) convertView.findViewById(R.id.iconoheader);
        switch (headerTitle) {
            case "Grúa":
                img.setImageResource(R.drawable.grua);
                break;
            case "Repuestos":
                img.setImageResource(R.drawable.engine);
                break;
            case "Centro de diagnóstico":
                img.setImageResource(R.drawable.icon);
                break;
            case "SOAT":
                img.setImageResource(R.drawable.safe);
                break;
            case "Accesorios":
                img.setImageResource(R.drawable.casco);
                break;
        }
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        TextView lblListHeader = (TextView) v.findViewById(R.id.lblListHeader);
        Toast.makeText(_context, "click"+lblListHeader.getText().toString(), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:123456789"));
        if (ActivityCompat.checkSelfPermission(_context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

        _context.startActivity(i);
        }
        return true;
    }
}
