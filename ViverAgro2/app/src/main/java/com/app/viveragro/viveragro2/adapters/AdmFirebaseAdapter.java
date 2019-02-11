package com.app.viveragro.viveragro2.adapters;

import com.app.viveragro.viveragro2.Carrinho;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

/**
 * Created by Matt on 11/08/2018.
 */

public abstract class AdmFirebaseAdapter extends FirebaseRecyclerAdapter<Adm,AdmViewHolder> {
    public AdmFirebaseAdapter(Class<Adm> modelClass, int modelLayout, Class<AdmViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected abstract void populateViewHolder(AdmViewHolder admViewHolder, Adm adm, int i);
}
