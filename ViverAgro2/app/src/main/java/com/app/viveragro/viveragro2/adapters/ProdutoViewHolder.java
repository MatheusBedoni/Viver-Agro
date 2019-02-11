package com.app.viveragro.viveragro2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.viveragro.viveragro2.R;

/**
 * Created by Matt on 15/01/2018.
 */

public class ProdutoViewHolder extends RecyclerView.ViewHolder {
    public TextView tvProdutoNome;
    public TextView tvProdutoPreco;
    public ImageView ivProduto;
    public TextView tvAval;
    public ImageView imageView;
    public ImageView editar;
    public TextView descricao;
    public ProdutoViewHolder(View itemView) {
        super(itemView);
        editar = (ImageView) itemView.findViewById(R.id.edit);
        ivProduto = (ImageView)itemView.findViewById(R.id.ivProfissaoFoto);
        tvProdutoNome = (TextView) itemView.findViewById(R.id.tvProfissionalNome);
        tvProdutoPreco = (TextView)itemView.findViewById(R.id.tvProfissionalProfissao);
        tvAval = (TextView)itemView.findViewById(R.id.tv_votos);
       descricao = (TextView)itemView.findViewById(R.id.tvDescricao);
        imageView = (ImageView)itemView.findViewById(R.id.imageView3);
    }
}
