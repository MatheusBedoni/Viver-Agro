package com.app.viveragro.viveragro2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import me.drakeet.materialdialog.MaterialDialog;

public class ConsumidorActivity extends AppCompatActivity implements ValueEventListener,Firebase.CompletionListener{
    private TextView toolbarTitle,nome,email;
    private Consumidor consumidor;
    private Produtor produtor;
    private Fretista fretista;
    private Button produtorButton,fretistaButton;
    private MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumidor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        produtor = new Produtor();
        fretista = new Fretista();
        produtorButton = (Button)findViewById(R.id.produtor);
        //fretistaButton = (Button)findViewById(R.id.fretista);
        nome = (TextView)findViewById(R.id.tvProfissionalNome) ;
        email = (TextView)findViewById(R.id.tvEmail);
        toolbarTitle = (TextView)findViewById(R.id.toolbartitle);
        consumidor = new Consumidor();
        consumidor.contextDataDB(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        produtorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMaterialDialog = new MaterialDialog(view.getContext())
                        .setTitle("Produtor")
                        .setMessage("Deseja que sua conta vire de produtor?")
                        .setPositiveButton("SIM", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                                produtor.setDataEntrada(consumidor.getDataEntrada());
                                produtor.setNomeProprietario(consumidor.getNome());
                                produtor.setEmail(consumidor.getEmail());
                                produtor.setId(consumidor.getId());
                                produtor.setSenha(consumidor.getSenha());
                                consumidor.removeDB();
                                produtor.saveTodosDB();
                                Intent intent = new Intent(getBaseContext(), Entrar.class);
                                startActivity(intent);
                                showToast("Agora você é um produtor, entre na sua nova conta");
                            }
                        })
                        .setNegativeButton("NAO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                            }
                        });

                mMaterialDialog.show();

            }
        });

    }

    @Override
    public void onComplete(FirebaseError firebaseError, Firebase firebase) {

    }
    protected void showToast(String message) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_LONG)
                .show();
    }
    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        try{
            Consumidor u = dataSnapshot.getValue(Consumidor.class);
            toolbarTitle.setText(u.getNome());
            consumidor = u;
            nome.setText(u.getNome());
            email.setText(u.getEmail());
        }catch (Exception e){

        }


    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
}
