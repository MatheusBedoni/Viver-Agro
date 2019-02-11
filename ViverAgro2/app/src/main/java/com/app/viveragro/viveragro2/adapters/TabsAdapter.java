package com.app.viveragro.viveragro2.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.viveragro.viveragro2.fragments.AvaliacoesFragment;
import com.app.viveragro.viveragro2.fragments.DescricaoFragment;
import com.app.viveragro.viveragro2.fragments.ProdutoFragment;


/**
 * Created by viniciusthiengo on 5/18/15.
 */
public class TabsAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private String[] titles = {"Produtos", "Descrição"};
   // private int[] icons = new int[]{R.drawable.car_1, R.drawable.car_1, R.drawable.car_2, R.drawable.car_3, R.drawable.car_4};
    private int heightIcon;
   

    Fragment fragment = new AvaliacoesFragment();
    public TabsAdapter(FragmentManager fm, Context c) {
        super(fm);

        mContext = c;
        double scale = c.getResources().getDisplayMetrics().density;
        heightIcon = (int)( 24 * scale + 0.5f );
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;

     if(position == 1){ // LUXURY CAR
            frag = new DescricaoFragment();
        }
        else if(position == 0){ // SPORT CAR
            frag = new ProdutoFragment();
        }
     
        Bundle b = new Bundle();
        b.putInt("position", position);

        frag.setArguments(b);

        return frag;
    }

    @Override
    public int getCount() {
        return titles.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        /*Drawable d = mContext.getResources().getDrawable( icons[position] );
        d.setBounds(0, 0, heightIcon, heightIcon);

        ImageSpan is = new ImageSpan( d );

        SpannableString sp = new SpannableString(" ");
        sp.setSpan( is, 0, sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );


        return ( sp );*/
        return ( titles[position] );
    }
}
