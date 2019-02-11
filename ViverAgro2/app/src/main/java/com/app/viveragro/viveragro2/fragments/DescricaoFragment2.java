package com.app.viveragro.viveragro2.fragments;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.viveragro.viveragro2.AdicionarDescricao;
import com.app.viveragro.viveragro2.AdicionarProdutos;
import com.app.viveragro.viveragro2.Produto;
import com.app.viveragro.viveragro2.Produtor;
import com.app.viveragro.viveragro2.ProdutorActivity;
import com.app.viveragro.viveragro2.ProdutorPerfil;
import com.app.viveragro.viveragro2.R;
import com.daimajia.easing.linear.Linear;

/**
 * Created by Matt on 26/12/2017.
 */

public class DescricaoFragment2 extends Fragment {
    public Produtor produtor;
    private LinearLayout comdesc;
    private LinearLayout semdesc;
    private TextView desc1,desc2,sitio,phone,endereco,propriedade;
    private Button criarDesc;
    private String descStr1,descStr2,sitios,phones,enderecos,propriedades;
    private FloatingActionButton fab;

    private Button editar;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        produtor = new Produtor();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.descricaofragment, container, false);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        try{
            descStr1 = ((ProdutorActivity) getActivity()).getDescricaoCurta();
           //descStr2 = ((ProdutorActivity) getActivity()).getDescricaoLonga();

            sitios = ((ProdutorActivity) getActivity()).getSitio();
            phones = ((ProdutorActivity) getActivity()).getPhone();
            enderecos = ((ProdutorActivity) getActivity()).getEndereco();
            propriedades = ((ProdutorActivity) getActivity()).getPropriedade();
        }catch (Exception e){

        }


        Log.i("LOG", "/////////frag: " + descStr1);
        Log.i("LOG", "/////////frag22:4 " + descStr2);
        semdesc = (LinearLayout)view.findViewById(R.id.semdesc);
        comdesc = (LinearLayout)view.findViewById(R.id.comdesc);
        desc1 = (TextView)view.findViewById(R.id.description);
        sitio = (TextView)view.findViewById(R.id.sitio);
        phone = (TextView)view.findViewById(R.id.phone);
        endereco = (TextView)view.findViewById(R.id.endereco);
        propriedade = (TextView)view.findViewById(R.id.propriedade);

            semdesc.setVisibility(View.GONE);
            comdesc.setVisibility(View.VISIBLE);
             desc1.setText(descStr1);
            //desc2.setText(descStr2);
            sitio.setText(sitios);
            phone.setText(phones);
            endereco.setText(enderecos);
            propriedade.setText(propriedades);


        return view;
    }
}
