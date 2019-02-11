package com.app.viveragro.viveragro2.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.viveragro.viveragro2.LibraryClass;
import com.app.viveragro.viveragro2.Produtor;
import com.app.viveragro.viveragro2.ProdutorActivity;
import com.app.viveragro.viveragro2.R;
import com.app.viveragro.viveragro2.adapters.ProdutoresFirebaseAdapter;
import com.app.viveragro.viveragro2.adapters.ProdutoresViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.client.Firebase;

public class ProdutoresFragment extends android.support.v4.app.Fragment {
    private RecyclerView rvUsers;
    private Firebase firebase;
   private ProdutoresFirebaseAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_produtores_fragment, container, false);
        rvUsers = (RecyclerView) view.findViewById(R.id.rv_list);
        rvUsers.setHasFixedSize(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        firebase = LibraryClass.getFirebase().child("produtor").child("Todos os produtores");

        try{
            rvUsers.setVisibility(View.VISIBLE);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            try {
                rvUsers.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                init();
                rvUsers.setAdapter(adapter);
            }catch (Exception e){}
        }catch (Exception e){}
    }

    public void init(){
        adapter = new ProdutoresFirebaseAdapter(
                Produtor.class,
                R.layout.item_produtores,
                ProdutoresViewHolder.class,
                firebase){
            @Override
            protected void populateViewHolder(ProdutoresViewHolder produtoresViewHolder, final Produtor produtor, int i) {
                super.populateViewHolder(produtoresViewHolder, produtor, i);
                produtoresViewHolder.tvProdutoNome.setText(produtor.getSitio());
                produtoresViewHolder.tvloc.setText(produtor.getEndereco());
                if(produtor.getEntrega() > 0){
                    produtoresViewHolder.tventregas.setText("Fazemos entregas");
                }else{
                    produtoresViewHolder.tventregas.setText("Não fazemos entregas");
                }
                produtoresViewHolder.tvAval.setVisibility(View.GONE);
                Glide
                        .with(getContext())
                        .load(produtor.getFoto())
                        .thumbnail(0.5f)
                        .centerCrop()
                        .placeholder(R.drawable.farmer)
                        .crossFade(50)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(produtoresViewHolder.ivProduto);
                produtoresViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), ProdutorActivity.class);
                        intent.putExtra("produtor",produtor);
                        getActivity().startActivity(intent);
                    }
                });
            }
        };

    }


}
