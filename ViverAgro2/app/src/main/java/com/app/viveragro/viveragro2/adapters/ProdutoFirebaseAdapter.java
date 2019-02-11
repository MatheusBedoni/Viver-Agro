package com.app.viveragro.viveragro2.adapters;

import com.app.viveragro.viveragro2.Produto;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

/**
 * Created by Matt on 15/01/2018.
 */

public  class ProdutoFirebaseAdapter  extends FirebaseRecyclerAdapter<Produto,ProdutoViewHolder> {
    public ProdutoFirebaseAdapter(Class<Produto> modelClass, int modelLayout, Class<ProdutoViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(ProdutoViewHolder produtoViewHolder, Produto produto, int i) {

    }

}
