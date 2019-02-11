package com.app.viveragro.viveragro2.adapters;

import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.viveragro.viveragro2.R;

/**
 * Created by Matt on 11/08/2018.
 */

public class AdmViewHolder extends RecyclerView.ViewHolder {
    public TextView telefonecomprador;
    public TextView telefonevendedor;
    public TextView precototal;
    public TextView data;
    public TextView numero;
    public TextView rua;
    public TextView bairro;
    public ImageView bttWhats;
    public ImageView bttPhone;
    public AdmViewHolder(View itemView) {
        super(itemView);
        data = (TextView)itemView.findViewById(R.id.data);
        telefonecomprador = (TextView)itemView.findViewById(R.id.txt_phone);
        telefonevendedor = (TextView)itemView.findViewById(R.id.nome);
        precototal = (TextView)itemView.findViewById(R.id.problema);
        numero = (TextView)itemView.findViewById(R.id.txt_numero2);
        rua = (TextView)itemView.findViewById(R.id.txt_rua2);
        bairro = (TextView)itemView.findViewById(R.id.txt_bairro2);
        bttPhone = (ImageView)itemView.findViewById(R.id.imageView5);
        bttWhats = (ImageView)itemView.findViewById(R.id.imageView6);
    }
}
