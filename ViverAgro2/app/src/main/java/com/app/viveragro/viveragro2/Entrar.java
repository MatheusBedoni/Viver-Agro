package com.app.viveragro.viveragro2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.viveragro.viveragro2.extras.VerificaErros;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Entrar extends AppCompatActivity {
    private String emailS;
    private String senhaS;
    private EditText email;
    private EditText senha;
    private ProgressDialog progress;
   private Firebase firebase;
   private Consumidor user;
   private Produtor produtor;
   private Fretista fretista;
   private CallbackManager callbackManager;
    private VerificaErros erros;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Fazer login com");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.corbranca));
        setSupportActionBar(toolbar);
        erros = new VerificaErros();
        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                accessFacebookLoginData( loginResult.getAccessToken() );
            }
            @Override
            public void onCancel() {}
            @Override
            public void onError(FacebookException error) {
                showToast( error.getMessage() );
            }
        });
        try{
            firebase = LibraryClass.getFirebase();
        }catch (Exception e){

        }
        progress = new ProgressDialog(this);
        produtor = new Produtor();
        fretista = new Fretista();
        user = new Consumidor();
        email  = (EditText)findViewById(R.id.edt_email);
        senha = (EditText)findViewById(R.id.etPassword);;

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e);
//                // ...
//            }
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    public void entrarfacebook(View view){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"));
    }
    private void accessFacebookLoginData(AccessToken accessToken){

        if(accessToken != null){
            firebase.authWithOAuthToken("facebook",accessToken.getToken(), new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(final AuthData authData) {

                    progress.setMessage("Carregando... ");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setIndeterminate(true);
                    progress.show();
                    user.saveTokenSP(Entrar.this, authData.getToken());
                    user.saveIdSP(Entrar.this, authData.getUid());
                    produtor.saveTokenSP(Entrar.this, authData.getToken());
                    produtor.saveIdSP(Entrar.this, authData.getUid());
                    fretista.saveTokenSP(Entrar.this, authData.getToken());
                    fretista.saveIdSP(Entrar.this, authData.getUid());
                    user.setNome(authData.getProviderData().get("displayName").toString());
                    user.setId(authData.getUid());

                    Firebase  firebase = LibraryClass.getFirebase();
                    firebase = firebase.child("consumidor").child("Todos os consumidores").child(authData.getUid());
                    firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Consumidor u = dataSnapshot.getValue(Consumidor.class);
                            if(u != null){
                                progress.cancel();
                                produtor.saveTipoSP(Entrar.this,"Consumidor");
                                Intent intent = new Intent(getBaseContext(),ConsumidorPerfil.class);
                                startActivity(intent);
                            }else {
                                Firebase  firebase = LibraryClass.getFirebase();
                                firebase = firebase.child("produtor").child("Todos os produtores").child(authData.getUid());
                                firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                       Produtor u = dataSnapshot.getValue(Produtor.class);
                                        if(u != null){
                                            progress.cancel();
                                            produtor.saveTipoSP(Entrar.this,"Produtor");
                                            Intent intent = new Intent(getBaseContext(), ProdutorPerfil.class);
                                            startActivity(intent);
                                        }else{
                                            progress.cancel();
                                            Intent intent = new Intent(getBaseContext(),RegistrarFacebook.class);
                                            intent.putExtra("usuario",user);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {

                                    }
                                });

                            }
                        }
                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            showToast(""+erros.getErro(firebaseError.getCode()));
                            progress.cancel();
                        }
                    });
                }
                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    showToast(""+erros.getErro(firebaseError.getCode()));

                }
            });
        }else{
            firebase.unauth();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    public void mudarSenha(View view){
        Intent intent = new Intent(this,MudarSenha.class);
        startActivity(intent);
    }

    public void entrar(View view){
        emailS = email.getText().toString();
        senhaS = senha.getText().toString();
        user.setEmail(emailS);
        user.setSenha(senhaS);
        if(emailS.trim().isEmpty() || senhaS.trim().isEmpty()){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Há dados em brancos");
            dlg.setNeutralButton("Ok",null);
            dlg.show();
        }else{
            progress.setMessage("Entrando... ");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();
            try{
                firebase.authWithPassword(
                        user.getEmail(),
                        user.getSenha(),
                        new Firebase.AuthResultHandler() {
                            @Override
                            public void onAuthenticated(final AuthData authData) {
                                produtor.saveTokenSP(Entrar.this, authData.getToken());
                                produtor.saveIdSP(Entrar.this, authData.getUid());
                                user.saveTokenSP(Entrar.this,authData.getUid());
                                user.saveIdSP(Entrar.this,authData.getUid());
                                fretista.saveTokenSP(Entrar.this, authData.getToken());
                                fretista.saveIdSP(Entrar.this, authData.getUid());
                                Firebase  firebase = LibraryClass.getFirebase();
                                firebase = firebase.child("produtor").child("Todos os produtores").child(authData.getUid());
                                firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Produtor u = dataSnapshot.getValue(Produtor.class);
                                        if(u != null){
                                            finish();
                                            produtor.saveTipoSP(Entrar.this,"Produtor");
                                            produtor.saveTipoSP(Entrar.this,"Produtor");
                                            Intent intent = new Intent(getBaseContext(),ProdutorPerfil.class);
                                            startActivity(intent);
                                            showToast("Bem vindo produtor");

                                        }else {
                                            produtor.saveTipoSP(Entrar.this,"Consumidor");
                                            user.saveTipoSP(Entrar.this,"Consumidor");
                                            Intent intent = new Intent(getBaseContext(),ConsumidorPerfil.class);
                                            startActivity(intent);
                                            showToast("Bem vindo consumidor");

                                        }
                                    }
                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {
                                        showToast(""+erros.getErro(firebaseError.getCode()));
                                        progress.cancel();
                                    }
                                });
                                progress.cancel();
                            }
                            @Override
                            public void onAuthenticationError(FirebaseError firebaseError) {
                                showToast(""+erros.getErro(firebaseError.getCode()));

                                progress.cancel();
                            }
                        }
                );

            }catch (Exception e){
                progress.cancel();
                finish();
            }
        }
    }
    public void cadastro(View view){
        Intent intent = new Intent(getBaseContext(),RegistrarActivity.class);
        startActivity(intent);
    }
    protected void showToast(String message) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_LONG)
                .show();
    }

}
