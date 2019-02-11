package com.app.viveragro.viveragro2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.viveragro.viveragro2.R;

/**
 * Created by Matt on 24/01/2018.
 */

public class ProdutoresViewHolder extends RecyclerView.ViewHolder{
    public TextView tvProdutoNome;
    public TextView tvProdutoPreco;
    public ImageView ivProduto;
    public TextView tvAval;
    public TextView tventregas;
    public ImageView imageView;
    public TextView tvloc;
    public ProdutoresViewHolder(View itemView) {
        super(itemView);
        tvloc = (TextView)itemView.findViewById(R.id.tvloc);
        tventregas = (TextView)itemView.findViewById(R.id.tventregas);
        ivProduto = (ImageView)itemView.findViewById(R.id.ivProfissaoFoto);
        tvProdutoNome = (TextView) itemView.findViewById(R.id.tvProfissionalNome);
        tvProdutoPreco = (TextView)itemView.findViewById(R.id.tvProfissionalProfissao);
        tvAval = (TextView)itemView.findViewById(R.id.tv_votos);
        imageView = (ImageView)itemView.findViewById(R.id.imageView3);
    }
}
