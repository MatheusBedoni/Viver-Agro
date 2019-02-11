package com.app.viveragro.viveragro2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.GregorianCalendar;
import java.util.Map;

public class RegistrarFacebook extends AppCompatActivity {
    private Toolbar mToolbar;
    private RelativeLayout rel1;
    private RelativeLayout rel2;
    private RelativeLayout rel4;
    private RelativeLayout rel3;
    private EditText nome;
    private EditText email;

    private EditText site;
    private EditText ddd;
    private ProgressDialog progress;
    private  AlertDialog.Builder dlg;
    private Button comecar_cadastro;
    private Button cancelar_cadastro;
    //
    private Produtor produtor;
    private Fretista fretista;
    private Consumidor user;

    private Firebase firebase;
    private FrameLayout frame;
    //
    private String nomeStr;
    private String emailStr;
    private String telStr;
    private String senhaStr;
    private String senhacomStr;
    private String siteStr;
    private String dddStr;
    private String cidade;
    private String estado;
    private Consumidor consumidor;
    //
    private int dataInt;
    int i = 4;
    private int escolha = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_facebook);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Registrar");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progress = new ProgressDialog(this);
        dlg = new AlertDialog.Builder(this);

        nome = (EditText) findViewById(R.id.nome);
        email = (EditText) findViewById(R.id.email);


//
        produtor = new Produtor();
//        try{
//            firebase = new Firebase("https://viveragroo.firebaseio.com/");
//
//        }catch (Exception e){
//            showToast(""+e);
//            email.setText(""+e);
//        }
        firebase = LibraryClass.getFirebase();


        user = new Consumidor();
        fretista = new Fretista();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getParcelable("usuario") != null) {
            consumidor = getIntent().getExtras().getParcelable("usuario");
        }
        nome.setText(consumidor.getNome());
    }

    public void carregar(){
        progress.setMessage("Salvando... ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }

    //
    protected void showToast(String message) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_LONG)
                .show();
    }
    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_remover) {
                //
                nomeStr = nome.getText().toString();
                emailStr = email.getText().toString();
                //
                telStr = " ";
                dddStr = " ";
                //
                if (emailStr.trim().isEmpty() || nomeStr.trim().isEmpty()) {
                    dlg.setMessage("Você precisa nos informar os dados");
                    dlg.setNeutralButton("Ok", null);
                    dlg.show();
                } else {


                            GregorianCalendar gc = new GregorianCalendar();
                            String mes, dia;
                            if (gc.get(gc.MONTH) >= 0 && gc.get(gc.MONTH) <= 9) {
                                int mesint = gc.get(gc.MONTH) + 1;
                                mes = "0" + mesint;
                            } else {
                                int mesint = gc.get(gc.MONTH) + 1;
                                mes = "" + mesint;
                            }
                            if (gc.get(gc.DAY_OF_MONTH) >= 1 && gc.get(gc.DAY_OF_MONTH) < 10) {
                                dia = "0" + gc.get(gc.DAY_OF_MONTH);
                            } else {
                                dia = "" + gc.get(gc.DAY_OF_MONTH);
                            }
                            String data = "" + gc.get(gc.YEAR) + "" + mes + "" + dia;
                            dataInt = Integer.parseInt(data);
                            dataInt *= -1;
                                    user.setId(consumidor.getId());
                                    user.setNome(nomeStr);
                                    user.setEmail(emailStr);
                                    user.setSenha(senhaStr);
                                    user.setTelefone(telStr);
                                    user.setDataEntrada(dataInt);
                                    user.saveTodosDB();
                                    firebase.unauth();
                                    //
                                    Intent intent = new Intent(getBaseContext(), Entrar.class);
                                    startActivity(intent);
                                    showToast("Conta  criada com sucesso");
                                    finish();
                        }

        }
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    //
    public void cancelar(View view){
        finish();
    }
    //
    public void termo(View view){
        Uri uri = Uri.parse("http://appconstroiai.wixsite.com/appdonorte/termos-de-uso");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    //

}
