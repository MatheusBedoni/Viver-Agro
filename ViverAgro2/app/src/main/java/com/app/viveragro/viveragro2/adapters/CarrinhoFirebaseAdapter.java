package com.app.viveragro.viveragro2.adapters;

import com.app.viveragro.viveragro2.Carrinho;
import com.app.viveragro.viveragro2.Produto;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

/**
 * Created by Matt on 08/02/2018.
 */

public class CarrinhoFirebaseAdapter extends FirebaseRecyclerAdapter<Carrinho,CarrinhoViewHolder> {
    public CarrinhoFirebaseAdapter(Class<Carrinho> modelClass, int modelLayout, Class<CarrinhoViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(CarrinhoViewHolder carrinhoViewHolder, Carrinho carrinho, int i) {

    }
}
