package com.app.viveragro.viveragro2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.viveragro.viveragro2.adapters.Adm;
import com.app.viveragro.viveragro2.adapters.Credito;
import com.app.viveragro.viveragro2.adapters.PagamentoService;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Random;

import cn.carbs.android.library.MDDialog;
import retrofit.GsonConverterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class FormadePagamento extends AppCompatActivity implements ValueEventListener,Firebase.CompletionListener{
    public TextView rua,bairro,numero;
    public Button adicionar;
    private String bairros,ruas;
    private int numeros;
    private  int escolha;
    private String id,idProd;
    private float preco;
    private Produtor produtor;
    private Consumidor consumidor;
    private Adm adm;


    private java.lang.String vendedor =
            "id_vend_princ:1234567890123456,\n" +
                    "id_vend_sec:123,\n" +
                    "token_vend_princ:01,\n" +
                    "porc_vend_princ:2013,\n" +
                    "porc_vend_secund:"
            ;
    private  java.lang.String comprador =
            "cpf:1234567890123456,\n" +
                    "nome:123,\n" +
                    "dt_nasc:01,\n" +
                    "telefone:2013,\n" +
                    "email:\n"+
                    "cep: \n"+
                    "num: \n"+
                    "comp:"
            ;

    private java.lang.String registros =  "nomecompleto: ,\n" +
            "numerocartao:,\n" +
            "cvccartao:,\n" +
            " escartao:,\n" +
            "anocartao:,\n" +
            "ckproprietario: ,\n" +
            "cpf:,\n" +
            "dtnascimento: ,\n" +
            "codigopais: ,\n" +
            "codigoestado: ,\n" +
            "fonecelular: ,\n" +
            "parcelas: ,\n" +
            "bandeiracartao: ,\n" +
            "inscricao: ,\n" +
            "participante: ,\n" +
            "evento: ,\n" +
            "banco: ";

    private  java.lang.String pedido =
            "num_pedido_vend:1234567890123456,\n" +
                    "desc_pedido:123,\n" +
                    "valor_total:01,\n" +
                    "moeda:2013,\n" +
                    "forma_pagam:\n"+
                    "cobrança:";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formade_pagamento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        adm = new Adm();
       adicionar = (Button)findViewById(R.id.adicionar);
        consumidor = new Consumidor();
        rua = (TextView) findViewById(R.id.tvrua);
        bairro = (TextView) findViewById(R.id.tvbairro);
        numero = (TextView) findViewById(R.id.tvnumero);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getParcelable("produtor") != null) {
            produtor = getIntent().getExtras().getParcelable("produtor");
        } else {
            Toast.makeText(this, "Fail!", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getParcelable("consumidor") != null) {
            consumidor = getIntent().getExtras().getParcelable("consumidor");
        } else {
            Toast.makeText(this, "Fail!", Toast.LENGTH_SHORT).show();
            finish();
        }
        Intent intent = getIntent();
        if (intent != null) {
            Bundle params = intent.getExtras();
            if (params != null) {
                id =   params.getString("id");
                idProd = params.getString("idProd");
                preco = params.getFloat("preco");
            }
        }
    }
    public void endereco(View view){
                new MDDialog.Builder(this)
                .setTitle("Digite o endereço da entrega")
               .setContentView(R.layout.qtd_endereco)
               .setNegativeButton("Cancelar", new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                    }
                })
                .setPositiveButton("Confirmar endereço", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View root = v.getRootView();

                        final Bundle params = new Bundle();
                        numeros = Integer.parseInt(getViewContent(root, R.id.numero));
                         bairros = getViewContent(root, R.id.bairro);
                       ruas = getViewContent(root, R.id.rua);
                        rua.setText("Rua: "+ruas);
                        bairro.setText("Bairro: "+bairros);
                        numero.setText("Número: "+numeros);
                        rua.setVisibility(View.VISIBLE);
                        bairro.setVisibility(View.VISIBLE);
                        numero.setVisibility(View.VISIBLE);
                        adicionar.setVisibility(View.GONE);

                    }
                })
                .create()
                .show();
    }
    private String getViewContent( View root, int id ){
        EditText field = (EditText) root.findViewById(id);
        return field.getText().toString();
    }
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
        return true;
    }

    public void pagamento(View view){
        if(escolha == 2){
            AlertDialog.Builder builder = new AlertDialog.Builder(FormadePagamento.this);
            builder.setTitle("Seu pedido foi enviado a esse produtor");
            builder.setMessage("Seu pedido foi enviado a esse produtor, espere que essse produtor confirme a sua entrega");
            builder.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ArrayList<String> list = new ArrayList<>();
                            ArrayList<String> cobraca = new ArrayList<>();
                            list.add(vendedor);
                            list.add(comprador);
                            list.add(pedido);
                            Gson gson = new GsonBuilder().registerTypeAdapter(Credito.class, new CreditoDes()).create();

                            Retrofit retrofit = new Retrofit
                                    .Builder()
                                    .baseUrl("https://app.ticketphone.com.br/webrun/")
                                    .addConverterFactory(JacksonConverterFactory.create())
                                    .build();
                            PagamentoService pagamentoService = retrofit.create(PagamentoService.class);
                            Call<Credito> call = pagamentoService.enviardados(1152,"4K3TB239FREADFU420KS2TJPKDDF734",list);
                            call.enqueue(new Callback<Credito>() {
                                @Override
                                public void onResponse(Call<Credito> call, retrofit2.Response<Credito> response) {
                                    Log.i("LOG", "Saveretrofit: " + response.toString());
                                    Log.i("LOG", "Saveretrofit: " + call.toString());
                                }

                                @Override
                                public void onFailure(Call<Credito> call, Throwable t) {
                                    Log.i("LOG", "Error SAVE: " + t.getMessage());
                                }
                            });
                            Carrinho carrinho = new Carrinho();
                            Random random = new Random();
                            int x = random.nextInt(1000);
                            adm.setNumero(""+numeros);
                            adm.setRua(ruas);
                            adm.setBairro(bairros);
                            adm.setTelefonecomprador(consumidor.getTelefone());
                            adm.setTelefonevendedor(produtor.getTelefone());
                            adm.setPrecototal(String.valueOf(preco));
                            adm.savePedidoDB(produtor.getTelefone()+x);
                            carrinho.setNome(consumidor.getNome());
                            carrinho.setNumero(""+numeros);
                            carrinho.setEndereco(bairros);
                            carrinho.setRua(ruas);
                            carrinho.setId(id);
                            carrinho.setPreco( String.valueOf(preco));
                            carrinho.pagamento(idProd,""+(1),1);
                            produtor.setPedidosPendentes(produtor.getPedidos()+1);
                            carrinho.removeDB(id+idProd);
                            atualizar();
                            Intent intent = new Intent(getBaseContext(),ConsumidorPerfil.class);
                            startActivity(intent);
                        }
                    });


            AlertDialog dialog = builder.create();
            // display dialog
            dialog.show();
        }else {
            new MDDialog.Builder(this)
                    .setTitle("Digite seu cartão")
                    .setContentView(R.layout.activity_pagamentos)
                    .setNegativeButton("Cancelar", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setPositiveButton("Confirmar cartão", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            View root = v.getRootView();

                            AlertDialog.Builder builder = new AlertDialog.Builder(FormadePagamento.this);
                            builder.setTitle("Seu pedido foi enviado a esse produtor");
                            builder.setMessage("Seu pedido foi enviado a esse produtor, espere que essse produtor confirme a sua entrega");
                            builder.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ArrayList<String> list = new ArrayList<>();
                                            ArrayList<String> cobraca = new ArrayList<>();
                                            list.add(vendedor);
                                            list.add(comprador);
                                            list.add(pedido);
                                            Gson gson = new GsonBuilder().registerTypeAdapter(Credito.class, new CreditoDes()).create();

                                            Retrofit retrofit = new Retrofit
                                                    .Builder()
                                                    .baseUrl("https://app.ticketphone.com.br/webrun/")
                                                    .addConverterFactory(JacksonConverterFactory.create())
                                                    .build();
                                            PagamentoService pagamentoService = retrofit.create(PagamentoService.class);
                                            Call<Credito> call = pagamentoService.enviardados(1152,"4K3TB239FREADFU420KS2TJPKDDF734",list);
                                            call.enqueue(new Callback<Credito>() {
                                                @Override
                                                public void onResponse(Call<Credito> call, retrofit2.Response<Credito> response) {
                                                    Log.i("LOG", "Saveretrofit: " + response.toString());
                                                    Log.i("LOG", "Saveretrofit: " + call.toString());
                                                }

                                                @Override
                                                public void onFailure(Call<Credito> call, Throwable t) {
                                                    Log.i("LOG", "Error SAVE: " + t.getMessage());
                                                }
                                            });
                                            Carrinho carrinho = new Carrinho();
                                            carrinho.setNome(consumidor.getNome());
                                            carrinho.setNumero(""+numeros);
                                            carrinho.setEndereco(bairros);
                                            carrinho.setRua(ruas);
                                            carrinho.setId(id);
                                            Random random = new Random();
                                            int x = random.nextInt(1000);
                                            adm.setNumero(""+numeros);
                                            adm.setRua(ruas);
                                            adm.setBairro(bairros);
                                            adm.setTelefonecomprador(consumidor.getTelefone());
                                            adm.setTelefonevendedor(produtor.getTelefone());
                                            adm.setPrecototal(String.valueOf(preco));
                                            adm.savePedidoDB(produtor.getTelefone()+x);
                                            carrinho.setPreco( String.valueOf(preco));
                                            carrinho.pagamento(idProd,""+1,1);
                                            produtor.setPedidosPendentes(produtor.getPedidos()+1);
                                            carrinho.removeDB(id+idProd);
                                            atualizar();

                                            Intent intent = new Intent(getBaseContext(),ConsumidorPerfil.class);
                                            startActivity(intent);
                                        }
                                    });


                            AlertDialog dialog = builder.create();
                            // display dialog
                            dialog.show();
                        }
                    })
                    .create()
                    .show();
        }
    }
    public void atualizar(){
        produtor.updateTodosProfDB(this);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radio_caartao:
                if (checked) {
                    escolha = 1;
                }
                break;
            case R.id.radio_dinheiro:
                if (checked) {
                    escolha = 2;
                }
                break;

        }
    }

    @Override
    public void onComplete(FirebaseError firebaseError, Firebase firebase) {

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
}
