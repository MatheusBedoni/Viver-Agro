package com.app.viveragro.viveragro2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.viveragro.viveragro2.fragments.ProdutoresConsumidorFragment;
import com.app.viveragro.viveragro2.fragments.ProdutoresFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.client.Firebase;

public class ProdtorConsumidor extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RelativeLayout relativeLayout;
    private Firebase firebase;
    private ProdutoresFragment produtoresFragment;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private View navHeader;
    private Produtor produtor;
    private DrawerLayout drawer;
    private ImageView fotoUsuario;
    private TextView nomeUsuario, emailUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodtor_consumidor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        //toolbar.setLogo(R.drawable.letrasdois);
        setSupportActionBar(toolbar);

        firebase = LibraryClass.getFirebase();


        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getParcelable("produtor") != null) {
            produtor = getIntent().getExtras().getParcelable("produtor");
        } else {
            Toast.makeText(this, "Fail!", Toast.LENGTH_SHORT).show();
            finish();
        }

        relativeLayout = (RelativeLayout) findViewById(R.id.rl_fragment_container);
        try{
            ProdutoresConsumidorFragment frag = (ProdutoresConsumidorFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
            if (frag == null) {
                frag = new ProdutoresConsumidorFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.rl_fragment_container, frag, "mainFrag");
                ft.commit();
            }
        }catch (Exception e){
            showToast(""+e);
        }
        fotoUsuario = (ImageView)findViewById(R.id.foto_do_usuario);
        try{
            Glide
                    .with(this)
                    .load(produtor.getFoto())
                    .thumbnail(0.5f)
                    .centerCrop()
                    .placeholder(R.drawable.placeholde)
                    .crossFade(50)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(fotoUsuario);
        }catch (Exception e){

        }
        fotoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),ProdutorPerfil.class);
                startActivity(intent);
            }
        });
    }



    protected void showToast(String message) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_LONG)
                .show();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.perfil){
            Intent intent = new Intent(getBaseContext(),ProdutorPerfil.class);
            startActivity(intent);
            finish();
        }
        if(id == R.id.action_pedidos){
            Intent intent = new Intent(getBaseContext(),PedidoActivity.class);
            intent.putExtra("produtor",produtor);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
