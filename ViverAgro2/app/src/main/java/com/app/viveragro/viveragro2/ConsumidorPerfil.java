package com.app.viveragro.viveragro2;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.viveragro.viveragro2.fragments.ProdutoresFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class ConsumidorPerfil extends AppCompatActivity implements ValueEventListener,Firebase.CompletionListener{
    private RelativeLayout relativeLayout;
    private Firebase firebase;
    private ProdutoresFragment produtoresFragment;
    private ImageView fotoUsuario;
    private Consumidor consumidor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumidor_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        //toolbar.setLogo(R.drawable.letrasdois);
        setSupportActionBar(toolbar);

        consumidor = new Consumidor();
        try{
            consumidor.contextDataDB(this);
        }catch (Exception e){

        }
        firebase = LibraryClass.getFirebase();
        fotoUsuario = (ImageView)findViewById(R.id.foto_do_usuario) ;
        Glide
                .with(getBaseContext())
                .load("")
                .thumbnail(0.5f)
                .centerCrop()
                .placeholder(R.drawable.user)
                .crossFade(50)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(fotoUsuario);
        fotoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),ConsumidorActivity.class);
                startActivity(intent);
            }
    });

        relativeLayout = (RelativeLayout) findViewById(R.id.rl_fragment_container);
        try{
            ProdutoresFragment frag = (ProdutoresFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
            if (frag == null) {
                frag = new ProdutoresFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.rl_fragment_container, frag, "mainFrag");
                ft.commit();
            }
        }catch (Exception e){
            showToast(""+e);
        }


    }

    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_perfil_dois, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sair) {

            firebase.unauth();
            Intent intent = new Intent(getBaseContext(),Entrar.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.action_remover) {

            firebase.removeUser(consumidor.getEmail(), consumidor.getSenha(), new Firebase.ResultHandler() {
                @Override
                public void onSuccess() {
                    consumidor.removeDB();
                    showToast("Sua conta foi removida");
                    Intent intent = new Intent(getBaseContext(),Entrar.class);
                    startActivity(intent);
                }
                @Override
                public void onError(FirebaseError firebaseError) {
                    showToast("Algo de errado aconteceu");
                }
            });

        }
//        if (id == R.id.action_share) {
//            Intent intent = new Intent(getBaseContext(),DestaqueActivity.class);
//            intent.putExtra("profissional",profissional);
//            startActivity(intent);
//        }
        return super.onOptionsItemSelected(item);
    }

    protected void showToast(String message) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_LONG)
                .show();
    }


    @Override
    public void onComplete(FirebaseError firebaseError, Firebase firebase) {

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Consumidor u = dataSnapshot.getValue(Consumidor.class);
       consumidor = u;
       if(consumidor.getEmail().equals("viveragro@gmail.com")){
           Intent intent = new Intent(getBaseContext(),AdmActivity.class);
           startActivity(intent);
       }

    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
}
