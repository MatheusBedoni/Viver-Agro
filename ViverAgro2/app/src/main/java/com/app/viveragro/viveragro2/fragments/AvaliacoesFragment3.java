package com.app.viveragro.viveragro2.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.viveragro.viveragro2.R;

/**
 * Created by Matt on 26/12/2017.
 */

public class AvaliacoesFragment3 extends Fragment {
    public TextView aval;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.avaliacoesfragment, container, false);
        aval = (TextView)view.findViewById(R.id.txt_nome);
        aval.setText("Esse produtor ainda não recebeu avaliações no seu perfil.");
        return view;
    }
}
