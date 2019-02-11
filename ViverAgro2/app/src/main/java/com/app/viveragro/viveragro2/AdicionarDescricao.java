package com.app.viveragro.viveragro2;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import me.drakeet.materialdialog.MaterialDialog;

public class AdicionarDescricao extends AppCompatActivity implements ValueEventListener,Firebase.CompletionListener {
    private EditText desc1,sitio,phone,endereco,propriedade;
    private Produtor produtot;
    private TextView pronto;
    private int escolha = 1;
    private RadioGroup radio1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_descricao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Editar perfil");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pronto = (TextView)findViewById(R.id.entrega);
        radio1 = (RadioGroup)findViewById(R.id.radioGroup);

        produtot = new Produtor();

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getParcelable("produtor") != null) {
            produtot = getIntent().getExtras().getParcelable("produtor");
        } else {
            Toast.makeText(this, "Fail!", Toast.LENGTH_SHORT).show();
            finish();
        }
        produtot.contextDataDB(this);

       desc1 = (EditText)findViewById(R.id.desc1);
        sitio = (EditText)findViewById(R.id.sitio);
        phone = (EditText)findViewById(R.id.tel);
        endereco = (EditText)findViewById(R.id.endereco);
        propriedade = (EditText)findViewById(R.id.propriedade);

    }


    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Produtor u = dataSnapshot.getValue(Produtor.class);
        produtot = u;

        sitio.setText(produtot.getSitio());
        phone.setText(produtot.getTelefone());
        endereco.setText(produtot.getEndereco());
        propriedade.setText(produtot.getPropriedade());
        if(produtot.getEntrega() > 0){
            radio1.setVisibility(View.GONE);
            pronto.setVisibility(View.GONE);
        }
        desc1.setText(produtot.getDescricaoCurta());
        Log.i("LOG", "/////////descricao1: " + produtot.getEmail());
        Log.i("LOG", "/////////descricao2 " + produtot.getNomeProprietario());
    }
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

            switch (view.getId()) {
                case R.id.radio_sim:
                    if (checked) {
                        escolha = 1;
                    }
                    break;
                case R.id.radio_nao:
                    if (checked) {
                        escolha = 0;
                    }
                    break;

            }

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

            String desc1Str = desc1.getText().toString();;
            String sitioStr = sitio.getText().toString();
            String telefone = phone.getText().toString();
            String enderecoStr = endereco.getText().toString();

            String propriedadeStr = propriedade.getText().toString();
            if (desc1Str.trim().isEmpty()) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setMessage("Há dados em brancos");
                dlg.setNeutralButton("Ok",null);
                dlg.show();
            }else{
                produtot.setDescricaoCurta(desc1Str);
                produtot.setSitio(sitioStr);
                produtot.setTelefone(telefone);
                produtot.setEndereco(enderecoStr);
                produtot.setPropriedade(propriedadeStr);
                produtot.setEntrega(escolha);
                produtot.updateTodosProfDB(this);
                Intent intent = new Intent(this, ProdutorPerfil.class);
                intent.putExtra("produtor",produtot);
                startActivity(intent);
            }
        }
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }

    @Override
    public void onComplete(FirebaseError firebaseError, Firebase firebase) {

    }

    public void cancelar(View view){
        finish();
    }
}
