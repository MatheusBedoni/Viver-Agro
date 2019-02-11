package com.app.viveragro.viveragro2;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.view.View;

import com.app.viveragro.viveragro2.adapters.Adm;
import com.app.viveragro.viveragro2.adapters.AdmFirebaseAdapter;
import com.app.viveragro.viveragro2.adapters.AdmViewHolder;
import com.firebase.client.Firebase;

public class AdmActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Firebase firebase;
    private RecyclerView rvUsers;
    private AdmFirebaseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm);
        toolbar = (Toolbar) findViewById(R.id.tb_main);
        toolbar.setTitle("Chamadas");
        toolbar.setTitleTextColor(getResources().getColor(R.color.corbranca));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvUsers = (RecyclerView) findViewById(R.id.rv_list);


    }

    @Override
    protected void onResume() {
        super.onResume();
        firebase = LibraryClass.getFirebase().child("adm");
        rvUsers.setHasFixedSize(true);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));

       adapter = new AdmFirebaseAdapter( Adm.class,
               R.layout.item_adm,
               AdmViewHolder.class,
               firebase) {
           @Override
           protected void populateViewHolder(AdmViewHolder admViewHolder, final Adm adm, int i) {
                admViewHolder.bairro.setText(adm.getBairro());
                admViewHolder.numero.setText(adm.getNumero());
                admViewHolder.precototal.setText(adm.getPrecototal());
                admViewHolder.telefonecomprador.setText(adm.getTelefonecomprador());
                admViewHolder.telefonevendedor.setText(adm.getTelefonevendedor());
                admViewHolder.data.setText(adm.getData());

               admViewHolder.bttWhats.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       try{
                           Intent intent = new Intent(Intent.ACTION_DIAL);
                           intent.setData(Uri.parse("tel:" +adm.getTelefonevendedor()));
                           startActivity(intent);
                       }catch (Exception e){

                       }
                   }

               });
               admViewHolder.bttPhone.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       try{
                           Intent intent = new Intent(Intent.ACTION_DIAL);
                           intent.setData(Uri.parse("tel:" +adm.getTelefonecomprador()));
                           startActivity(intent);
                       }catch (Exception e){

                       }
                   }

               });

           }
       };

        rvUsers.setAdapter(adapter);
        rvUsers.setOnClickListener(new RecyclerView.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });
    }
}
