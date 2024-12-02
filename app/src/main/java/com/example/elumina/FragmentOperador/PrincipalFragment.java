package com.example.elumina.FragmentOperador;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.elumina.Fragment.SolicitarMantenimientoFragment;
import com.example.elumina.R;

public class PrincipalFragment extends Fragment {

    public PrincipalFragment() {
        // Constructor vacío requerido
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View rootView = inflater.inflate(R.layout.operador_principal, container, false);

        ImageView gifImageView = rootView.findViewById(R.id.gif_us);

        // Usar Glide para cargar el GIF desde una URL o un recurso local
        Glide.with(this)
                .load(R.drawable.gif_usuario) // Puedes usar una URL o un recurso local
                .into(gifImageView);



        return rootView;
    }
}