package com.app.viveragro.viveragro2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crash.FirebaseCrash;

public class MudarSenha extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mudar_senha);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Senha");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.corbranca));
        firebaseAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.edt_email);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return true;
    }

    public void enviar(View view){
        try{
            firebaseAuth
                    .sendPasswordResetEmail( email.getText().toString() )
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if( task.isSuccessful() ){
                                email.setText("");
                                finish();
                                Toast.makeText(
                                        MudarSenha.this,
                                        "Recuperação de acesso iniciada. Email enviado.",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                            else{
                                finish();
                                Toast.makeText(
                                        MudarSenha.this,
                                        "Falhou! Tente novamente",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            FirebaseCrash.report( e );
                        }
                    });
        }catch (Exception e){

        }

    }
}
