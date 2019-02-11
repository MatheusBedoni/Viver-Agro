package com.app.viveragro.viveragro2.adapters;

import com.app.viveragro.viveragro2.Produto;
import com.app.viveragro.viveragro2.Produtor;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

/**
 * Created by Matt on 24/01/2018.
 */

public class ProdutoresFirebaseAdapter extends FirebaseRecyclerAdapter<Produtor,ProdutoresViewHolder> {
    public ProdutoresFirebaseAdapter(Class<Produtor> modelClass, int modelLayout, Class<ProdutoresViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(ProdutoresViewHolder produtoresViewHolder, Produtor produtor, int i) {

    }
}
