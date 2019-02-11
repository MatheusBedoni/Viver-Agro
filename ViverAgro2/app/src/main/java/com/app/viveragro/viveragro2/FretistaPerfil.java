package com.app.viveragro.viveragro2;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class FretistaPerfil extends AppCompatActivity implements ValueEventListener,Firebase.CompletionListener{
    private Fretista fretista;
    private TextView toolbarTitle,nome,contato,descricao,tipo,habilitacao;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fretista_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fretista = new Fretista();
        fretista.contextDataDB(this);

        image = (ImageView)findViewById(R.id.ivProfissaoFoto);
        toolbarTitle = (TextView)findViewById(R.id.toolbartitle);
        nome = (TextView)findViewById(R.id.nome);
        contato = (TextView)findViewById(R.id.contato);
        tipo = (TextView)findViewById(R.id.tipocarro);
        descricao = (TextView)findViewById(R.id.description);
        habilitacao = (TextView)findViewById(R.id.habilitacao);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onComplete(FirebaseError firebaseError, Firebase firebase) {

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Fretista u = dataSnapshot.getValue(Fretista.class);
        fretista = u;
        toolbarTitle.setText(fretista.getNome());
        nome.setText(fretista.getNome());
        contato.setText(fretista.getTelefone());
        tipo.setText(fretista.getCarro());
        descricao.setText(fretista.getDescricao());
        habilitacao.setText(fretista.getHabilitacao());
        Glide
                .with(getBaseContext())
                .load(fretista.getFoto())
                .thumbnail(0.5f)
                .centerCrop()
                .placeholder(R.drawable.pick_up)
                .crossFade(50)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
}
