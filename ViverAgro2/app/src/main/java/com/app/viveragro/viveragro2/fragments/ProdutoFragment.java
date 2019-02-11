package com.app.viveragro.viveragro2.fragments;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.viveragro.viveragro2.AdicionarProdutos;
import com.app.viveragro.viveragro2.EditarProdutos;
import com.app.viveragro.viveragro2.LibraryClass;
import com.app.viveragro.viveragro2.Produto;
import com.app.viveragro.viveragro2.Produtor;
import com.app.viveragro.viveragro2.ProdutorActivity;
import com.app.viveragro.viveragro2.ProdutorPerfil;
import com.app.viveragro.viveragro2.R;
import com.app.viveragro.viveragro2.adapters.ProdutoFirebaseAdapter;
import com.app.viveragro.viveragro2.adapters.ProdutoViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import cn.carbs.android.library.MDDialog;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Matt on 26/12/2017.
 */

public class ProdutoFragment extends Fragment implements ValueEventListener,Firebase.CompletionListener {
    private Button addProdutos;
    private Produtor produtor;
    public Produto produto;
    public int qtdProdutos =3;
    private FloatingActionButton fab;
    public RecyclerView rvUsers;
    private ProdutoFirebaseAdapter adapter;
    private Firebase firebase;
    private LinearLayout add;
    private  MaterialDialog mMaterialDialog;
    private String id = " ";
    private RelativeLayout semproduto;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        produtor = new Produtor();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.produtofragment, container, false);
        produto = new Produto();
        add = (LinearLayout)view.findViewById(R.id.add);
        try{
            qtdProdutos =  ((ProdutorPerfil) getActivity()).getQtdProdutos();
            id = ((ProdutorPerfil)getActivity()).getId();
        }catch (Exception e){

        }

      //  semproduto = (RelativeLayout)view.findViewById(R.id.);
        addProdutos = (Button) view.findViewById(R.id.addProdutos);
        rvUsers = (RecyclerView) view.findViewById(R.id.rv_list);
        rvUsers.setHasFixedSize(true);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        Log.i("LOG", "/////////qtd: " +qtdProdutos);
        if(qtdProdutos > 0){
            rvUsers.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
            addProdutos.setVisibility(View.GONE);
            add.setVisibility(View.GONE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), AdicionarProdutos.class);
                    intent.putExtra("produtor",produtor);
                    startActivity(intent);
                }
            });
        }else{
            Log.i("LOG", "/////////Nome2: " + produtor.getEmail());
            addProdutos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), AdicionarProdutos.class);
                    intent.putExtra("produtor",produtor);
                    startActivity(intent);
                }
            });
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            firebase = LibraryClass.getFirebase().child("produto").child(id);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rvUsers.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            init();
            rvUsers.setAdapter(adapter);
        }catch (Exception e){

        }
    }
    void init(){
        adapter = new ProdutoFirebaseAdapter(
                Produto.class,
                R.layout.item_produto,
                ProdutoViewHolder.class,
                firebase){

            @Override
            protected void populateViewHolder(ProdutoViewHolder produtoViewHolder, final Produto produto, int i) {
                Log.i("LOG", "///////////////////////////////////////////////////");
                Log.i("LOG", "///////////////////////////////////////////////////");
               produtoViewHolder.editar.setVisibility(View.VISIBLE);
                produtoViewHolder.descricao.setText(produto.getDescricao());
                produtoViewHolder.tvProdutoNome.setText(produto.getProduto());
                produtoViewHolder.tvProdutoPreco.setText(produto.getPreco()+" R$");
                produtoViewHolder.tvProdutoPreco.setTextColor(getResources().getColor(R.color.colorPrimary));
                produtoViewHolder.editar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), EditarProdutos.class);
                        intent.putExtra("produto",produto);
                        startActivity(intent);
                    }
                });
                Glide
                        .with(getContext())
                        .load(R.drawable.letter_x)
                        .thumbnail(0.5f)
                        .centerCrop()
                        .placeholder(R.drawable.letter_x)
                        .crossFade(50)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(produtoViewHolder.imageView);
                produtoViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mMaterialDialog = new MaterialDialog(getContext())
                                .setTitle("Você deseja excluir esse produto?")
                                .setPositiveButton("SIM", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mMaterialDialog.dismiss();
                                        produto.removeDB(id);
                                    }
                                })
                                .setNegativeButton("NAO", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mMaterialDialog.dismiss();
                                    }
                                });
                        mMaterialDialog.show();
                    }
                });
                if(produto.getProduto().equals("Tomate")){
                    Glide
                            .with(getContext())
                            .load(produto.getUrl())
                            .thumbnail(0.5f)
                            .centerCrop()
                            .placeholder(R.drawable.tomatoz)
                            .crossFade(50)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(produtoViewHolder.ivProduto);
                }
                if(produto.getProduto().equals("Laranja")){
                    Glide
                            .with(getContext())
                            .load(produto.getUrl())
                            .thumbnail(0.5f)
                            .centerCrop()
                            .placeholder(R.drawable.orangez)
                            .crossFade(50)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(produtoViewHolder.ivProduto);
                }
                if(produto.getProduto().equals("Alface")){
                    Glide
                            .with(getContext())
                            .load(produto.getUrl())
                            .thumbnail(0.5f)
                            .centerCrop()
                            .placeholder(R.drawable.lettucez)
                            .crossFade(50)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(produtoViewHolder.ivProduto);
                }
                if(produto.getProduto().equals("Cenoura")){
                    Glide
                            .with(getContext())
                            .load(produto.getUrl())
                            .thumbnail(0.5f)
                            .centerCrop()
                            .placeholder(R.drawable.carrotz)
                            .crossFade(50)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(produtoViewHolder.ivProduto);
                }
                if(produto.getProduto().equals("Abóbora")){
                    Glide
                            .with(getContext())
                            .load(produto.getUrl())
                            .thumbnail(0.5f)
                            .placeholder(R.drawable.pumpkinz)
                            .crossFade(50)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(produtoViewHolder.ivProduto);
                }
                if(produto.getProduto().equals("Banana")){
                    Glide
                            .with(getContext())
                            .load(produto.getUrl())
                            .thumbnail(0.5f)
                            .centerCrop()
                            .placeholder(R.drawable.bananasz)
                            .crossFade(50)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(produtoViewHolder.ivProduto);
                }
                if(produto.getProduto().equals("Batata doce")){
                    Glide
                            .with(getContext())
                            .load(produto.getUrl())
                            .thumbnail(0.5f)
                            .centerCrop()
                            .placeholder(R.drawable.sweet_potatoz)
                            .crossFade(50)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(produtoViewHolder.ivProduto);
                }
                if(produto.getProduto().equals("Batata")){
                    Glide
                            .with(getContext())
                            .load(produto.getUrl())
                            .thumbnail(0.5f)
                            .centerCrop()
                            .placeholder(R.drawable.potatoz)
                            .crossFade(50)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(produtoViewHolder.ivProduto);
                }
                if(produto.getProduto().equals("Melancia")){
                    Glide
                            .with(getContext())
                            .load(produto.getUrl())
                            .thumbnail(0.5f)
                            .centerCrop()
                            .placeholder(R.drawable.watermelonz)
                            .crossFade(50)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(produtoViewHolder.ivProduto);
                }
                if(produto.getProduto().equals("Leite")){
                    Glide
                            .with(getContext())
                            .load(produto.getUrl())
                            .thumbnail(0.5f)
                            .centerCrop()
                            .placeholder(R.drawable.milk_bottle)
                            .crossFade(50)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(produtoViewHolder.ivProduto);
                }
                if(produto.getProduto().equals("Mel")){
                    Glide
                            .with(getContext())
                            .load(produto.getUrl())
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
    public void onComplete(FirebaseError firebaseError, Firebase firebase) {}
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {}
    @Override
    public void onCancelled(FirebaseError firebaseError) {}
}
