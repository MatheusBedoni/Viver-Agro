package com.app.viveragro.viveragro2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.viveragro.viveragro2.adapters.CarrinhoFirebaseAdapter;
import com.app.viveragro.viveragro2.adapters.CarrinhoViewHolder;
import com.app.viveragro.viveragro2.adapters.ProdutoFirebaseAdapter;
import com.app.viveragro.viveragro2.adapters.ProdutoViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.w3c.dom.Text;

import cn.carbs.android.library.MDDialog;
import me.drakeet.materialdialog.MaterialDialog;

public class CarrinhoActivity extends AppCompatActivity implements ValueEventListener,Firebase.CompletionListener{
    public RecyclerView rvUsers;
    private CarrinhoFirebaseAdapter adapter;
    private Firebase firebase;
    private String id,idProd;
    private float preco;
    private MaterialDialog mMaterialDialog;
    private Button pagamento, cancelar;
    private Produtor produtor;
    private Consumidor consumidor;
    private TextView total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        total = (TextView)findViewById(R.id.tvtotal);
        produtor = new Produtor();
        consumidor = new Consumidor();
        pagamento = (Button)findViewById(R.id.comprar);

        rvUsers = (RecyclerView) findViewById(R.id.rv_list);
        rvUsers.setHasFixedSize(true);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle params = intent.getExtras();
            if (params != null) {
                id =   params.getString("id");
                idProd = params.getString("idProd");
                preco = params.getFloat("preco");
                total.setText("Total: " +preco);
            }
        }

    }
    //
    protected void showToast(String message) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_LONG)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        firebase = LibraryClass.getFirebase().child("carrinho").child(id+idProd);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvUsers.setLayoutManager(llm);
        init();
        rvUsers.setAdapter(adapter);


    }
    private String getViewContent( View root, int id ){
        EditText field = (EditText) root.findViewById(id);
        return field.getText().toString();
    }

    public void pagamento(View view){

        final Bundle params = new Bundle();
        params.putString("id",id);
        params.putString("idProd",idProd);
        params.putFloat("preco",preco);
        Intent intent = new Intent(getBaseContext(),FormadePagamento.class);
        intent.putExtras(params);
        intent.putExtra("produtor",produtor);
        intent.putExtra("consumidor",consumidor);
        startActivity(intent);

    }
    public void atualizar(){
        produtor.updateTodosProfDB(this);
    }
    public void cancelar(View view){
        finish();Carrinho carrinho = new Carrinho();
        carrinho.removeDB(id+idProd);
        carrinho.removePedidoDB(idProd);
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
            Carrinho carrinho = new Carrinho();
            carrinho.removeDB(id+idProd);
            carrinho.removePedidoDB(idProd);
            finish();
        }
        return true;
    }

    void init(){
        adapter = new CarrinhoFirebaseAdapter(
                Carrinho.class,
                R.layout.item_produto_pedido,
                CarrinhoViewHolder.class,
                firebase){

            @Override
            protected void populateViewHolder(CarrinhoViewHolder produtoViewHolder, Carrinho produto, int i) {
                Log.i("LOG", "///////////////////////////////////////////////////");
                Log.i("LOG", "///////////////////////////////////////////////////");
                produtoViewHolder.tvProdutoNome.setText(produto.getProduto());
                produtoViewHolder.tvProdutoPreco.setText(produto.getQtd()+" un");
                produtoViewHolder.tvAval.setText("R$ "+produto.getPreco());
                if(produto.getProduto().equals("Tomate")){
                    Glide
                            .with(getApplicationContext())
                            .load(produto.getFoto())
                            .thumbnail(0.5f)
                            .centerCrop()
                            .placeholder(R.drawable.tomatoz)
                            .crossFade(50)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(produtoViewHolder.ivProduto);
                }
                if(produto.getProduto().equals("Laranja")){
                    Glide
                            .with(getApplicationContext())
                            .load(produto.getFoto())
                            .thumbnail(0.5f)
                            .centerCrop()
                            .placeholder(R.drawable.orangez)
                            .crossFade(50)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(produtoViewHolder.ivProduto);
                }
                if(produto.getProduto().equals("Alface")){
                    Glide
                            .with(getApplicationContext())
                            .load(produto.getFoto())
                            .thumbnail(0.5f)
                            .centerCrop()
                            .placeholder(R.drawable.lettucez)
                            .crossFade(50)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(produtoViewHolder.ivProduto);
                }
                if(produto.getProduto().equals("Cenoura")){
                    Glide
                            .with(getApplicationContext())
                            .load(produto.getFoto())
                            .thumbnail(0.5f)
                            .centerCrop()
                            .placeholder(R.drawable.carrotz)
                            .crossFade(50)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(produtoViewHolder.ivProduto);
                }
                if(produto.getProduto().equals("Abóbora")){
                    Glide
                            .with(getApplicationContext())
                            .load(produto.getFoto())
                            .thumbnail(0.5f)
                            .placeholder(R.drawable.pumpkinz)
                            .crossFade(50)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(produtoViewHolder.ivProduto);
                }
                if(produto.getProduto().equals("Banana")){
                    Glide
                            .with(getApplicationContext())
                            .load(produto.getFoto())
                            .thumbnail(0.5f)
                            .centerCrop()
                            .placeholder(R.drawable.bananasz)
                            .crossFade(50)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(produtoViewHolder.ivProduto);
                }
                if(produto.getProduto().equals("Batata doce")){
                    Glide
                            .with(getApplicationContext())
                            .load(produto.getFoto())
                            .thumbnail(0.5f)
                            .centerCrop()
                            .placeholder(R.drawable.sweet_potatoz)
                            .crossFade(50)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(produtoViewHolder.ivProduto);
                }
                if(produto.getProduto().equals("Batata")){
                    Glide
                            .with(getApplicationContext())
                            .load(produto.getFoto())
                            .thumbnail(0.5f)
                            .centerCrop()
                            .placeholder(R.drawable.potatoz)
                            .crossFade(50)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(produtoViewHolder.ivProduto);
                }
                if(produto.getProduto().equals("Melancia")){
                    Glide
                            .with(getApplicationContext())
                            .load(produto.getFoto())
                            .thumbnail(0.5f)
                            .centerCrop()
                            .placeholder(R.drawable.watermelonz)
                            .crossFade(50)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(produtoViewHolder.ivProduto);
                }
                if(produto.getProduto().equals("Leite")){
                    Glide
                            .with(getApplicationContext())
                            .load(produto.getFoto())
                            .thumbnail(0.5f)
                            .centerCrop()
                            .placeholder(R.drawable.milk_bottle)
                            .crossFade(50)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(produtoViewHolder.ivProduto);
                }
                if(produto.getProduto().equals("Mel")){
                    Glide
                            .with(getApplicationContext())
                            .load(produto.getFoto())
                            .thumbnail(0.5f)
                            .centerCrop()
                            .placeholder(R.drawable.honeyz)
                            .crossFade(50)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(produtoViewHolder.ivProduto);
                }

                //


            }
        };



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
