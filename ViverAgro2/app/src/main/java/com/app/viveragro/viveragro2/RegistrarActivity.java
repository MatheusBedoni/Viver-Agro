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

import com.app.viveragro.viveragro2.extras.VerificaErros;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.GregorianCalendar;
import java.util.Map;

public class RegistrarActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RelativeLayout rel1;
    private RelativeLayout rel2;
    private RelativeLayout rel4;
    private RelativeLayout rel3;
    private EditText nome;
    private EditText email;
    private EditText senha;
    private EditText senhacom;
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
    private EditText tel;
    //
    private VerificaErros erros;

    private int dataInt;
    int i = 4;
    private int escolha = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Registrar");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tel = (EditText) findViewById(R.id.tel);
        progress = new ProgressDialog(this);
        dlg = new AlertDialog.Builder(this);
        erros = new VerificaErros();

        nome = (EditText) findViewById(R.id.nome);
        email = (EditText) findViewById(R.id.email);

        senha = (EditText) findViewById(R.id.senha);
        senhacom = (EditText) findViewById(R.id.senhacom);
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

    public void carregar(){
        progress.setMessage("Salvando... ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
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
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_remover) {
                nomeStr = nome.getText().toString();
                emailStr = email.getText().toString();
                telStr = tel.getText().toString();
                //

                //
                if (emailStr.trim().isEmpty() || nomeStr.trim().isEmpty()) {
                    dlg.setMessage("Você precisa nos informar os dados");
                    dlg.setNeutralButton("Ok", null);
                    dlg.show();
                } else {

                    // showToast("f"+nomeStr+ emailStr + telStr + senhaStr);

                    //
                    senhaStr = senha.getText().toString();
                    senhacomStr = senhacom.getText().toString();
                    //
                    if (senhaStr.trim().isEmpty() || senhacomStr.trim().isEmpty()) {
                        dlg.setMessage("Você precisa de uma senha");
                        dlg.setNeutralButton("Ok", null);
                        dlg.show();
                    } else {

                        if (senhaStr.length() > 5) {
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
                            if (senhaStr.equals(senhacomStr)) {
                                progress.setMessage("Carregando... ");
                                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                progress.setIndeterminate(true);
                                progress.show();
                                    user.setId("" + 1);
                                    user.setNome(nomeStr);
                                    user.setEmail(emailStr);
                                    user.setSenha(senhaStr);
                                    user.setTelefone(telStr);
                                    user.setDataEntrada(dataInt);
                                    try{
                                        firebase.createUser(
                                                user.getEmail(),
                                                user.getSenha(),
                                                new Firebase.ValueResultHandler<Map<String, Object>>() {
                                                    @Override
                                                    public void onSuccess(Map<String, Object> stringObjectMap) {
                                                        progress.dismiss();
                                                        //  user.saveTokenSP(Registrar.this,stringObjectMap.get("token").toString());
                                                        user.saveIdSP(RegistrarActivity.this, stringObjectMap.get("uid").toString());

                                                        user.setId(stringObjectMap.get("uid").toString());
                                                        user.saveTodosDB();
                                                        firebase.unauth();
                                                        showToast("Conta  criada com sucesso, entre na sua conta");
                                                        Intent intent = new Intent(getBaseContext(), Entrar.class);
                                                        startActivity(intent);
                                                    }

                                                    @Override
                                                    public void onError(FirebaseError firebaseError) {
                                                        showToast(""+erros.getErro(firebaseError.getCode()));
                                                        // showToast("Aconteceu algum problema na criação de sua conta");
                                                        progress.dismiss();

                                                    }
                                                }
                                        );
                                    }catch (Exception e){

                                    }

                                }else{
                                dlg.setMessage("As senha não correspondem");
                                dlg.setNeutralButton("Ok", null);
                                dlg.show();
                                }
                        }else{
                            dlg.setMessage("As senha precisa ter mais de cinco caracteres");
                            dlg.setNeutralButton("Ok", null);
                            dlg.show();
                        }


                    }
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
