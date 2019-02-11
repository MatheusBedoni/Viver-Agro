package com.app.viveragro.viveragro2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.viveragro.viveragro2.adapters.CarrinhoFirebaseAdapter;
import com.app.viveragro.viveragro2.adapters.CarrinhoViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import me.drakeet.materialdialog.MaterialDialog;

import static android.view.View.GONE;

public class PedidoActivity extends AppCompatActivity implements ValueEventListener,Firebase.CompletionListener{
    public RecyclerView rvUsers;
    private CarrinhoFirebaseAdapter adapter;
    private Firebase firebase, firebase2;
    private String id,idProd;
    private float preco;
    private MaterialDialog mMaterialDialog;
    private Button pagamento, cancelar;
    private Produtor produtor;
    private LinearLayout ped,add;
    private TextView rua,bairro,numero,nome,telefone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        ped = (LinearLayout)findViewById(R.id.ped);
        add = (LinearLayout)findViewById(R.id.add);
        pagamento = (Button)findViewById(R.id.comprar);

        nome = (TextView)findViewById(R.id.tvProfissionalNome);
        telefone = (TextView)findViewById(R.id.tvtelefone);
        rua = (TextView)findViewById(R.id.tvrua);
        numero = (TextView)findViewById(R.id.tvnumero);
        bairro = (TextView)findViewById(R.id.tvbairro);
        produtor = new Produtor();

        rvUsers = (RecyclerView) findViewById(R.id.rv_list);
        rvUsers.setHasFixedSize(true);

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
    public  void negar(View view){
        produtor.setPedidos(0);
        produtor.setPedidosPendentes(0);
        produtor.updateTodosProfDB(this);
        finish();
    }
    public  void confimar(View view){
        mMaterialDialog = new MaterialDialog(this)
                .setTitle("O consumidor irá receber uma mensagem de confirmação de entrega")
                .setPositiveButton("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                        finish();
                    }
                });

        mMaterialDialog.show();
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

        if(produtor.getPedidosPendentes()> 0){

            firebase = LibraryClass.getFirebase().child("pedido").child(produtor.getId()).child(""+produtor.getPedidos());
            firebase2 = LibraryClass.getFirebase().child("pagamento").child(produtor.getId()).child(""+1);
            firebase2.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Carrinho u = dataSnapshot.getValue( Carrinho.class );
                    nome.setText(u.getNome());
                    telefone.setText(u.getTelefone());
                    rua.setText("Rua: "+u.getRua());
                    bairro.setText("Bairro: "+u.getEndereco());
                    numero.setText("Número: "+u.getNumero());
                }
                //
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                //
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}
                //
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                //
                @Override
                public void onCancelled(FirebaseError firebaseError) {}
                //
            });
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rvUsers.setLayoutManager(llm);
            init();
            rvUsers.setAdapter(adapter);
        }else{
            ped.setVisibility(View.GONE);
            add.setVisibility(View.VISIBLE);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            finish();

        }
        return true;
    }
    public void init(){
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
