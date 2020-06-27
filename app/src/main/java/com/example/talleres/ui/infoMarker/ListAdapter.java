package com.example.talleres.ui.infoMarker;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.talleres.R;
import com.example.talleres.ui.servicios.Servicio;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ListAdapter extends BaseAdapter {

    private Context _context;
    private ArrayList<Comentario> listComments;

    public ListAdapter(Context context, ArrayList<Comentario> listComments) {
        this._context = context;
       this.listComments=listComments;
    }


    @Override
    public int getCount() {

        return listComments.size();
    }

    @Override
    public Object getItem(int position) {
        return listComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(String.valueOf(position));
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comentario comentario = listComments.get(position);
        final String usuario = comentario.nombreUsuario;
        final String comentarioDet = comentario.comentario;


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fragment_comment, null);
        }

        TextView txtUsuario =(TextView) convertView
                .findViewById(R.id.itemUsuario);
        TextView txtComentario = (TextView) convertView
                .findViewById(R.id.itemComentario);
        txtUsuario.setText(usuario);
        txtComentario.setText(comentarioDet);
        return convertView;
    }


}
