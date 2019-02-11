package com.app.viveragro.viveragro2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.viveragro.viveragro2.R;

/**
 * Created by Matt on 08/02/2018.
 */

public class CarrinhoViewHolder  extends RecyclerView.ViewHolder{
    public TextView tvProdutoNome;
    public TextView tvProdutoPreco;
    public ImageView ivProduto;
    public TextView tvAval;
    public TextView descricao;

    public ImageView imageView;
    public CarrinhoViewHolder(View itemView) {
        super(itemView);
        ivProduto = (ImageView)itemView.findViewById(R.id.ivProfissaoFoto);
        tvProdutoNome = (TextView) itemView.findViewById(R.id.tvProfissionalNome);
        tvProdutoPreco = (TextView)itemView.findViewById(R.id.tvunidade);
        tvAval = (TextView)itemView.findViewById(R.id.tvpreco);
    }
}
