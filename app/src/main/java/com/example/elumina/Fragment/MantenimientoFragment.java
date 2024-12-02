package com.example.elumina.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.elumina.R;

public class MantenimientoFragment extends Fragment {

    public MantenimientoFragment() {
        // Constructor vacío requerido
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_mantenimiento, container, false);
        ImageView gifImageView = rootView.findViewById(R.id.gifff);

        Glide.with(getContext())
                .asGif()
                .load(R.drawable.gif)
                .into(gifImageView);

        View mantenimientoCliente = rootView.findViewById(R.id.mantenimiento_cliente);
        mantenimientoCliente.setOnClickListener(v -> {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, new SolicitarMantenimientoFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return rootView;
    }
}