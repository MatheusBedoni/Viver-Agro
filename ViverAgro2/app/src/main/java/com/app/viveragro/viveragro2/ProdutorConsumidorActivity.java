package com.app.viveragro.viveragro2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.viveragro.viveragro2.adapters.TabsAdapter;
import com.app.viveragro.viveragro2.adapters.TabsAdapter2;
import com.app.viveragro.viveragro2.adapters.TabsAdapter3;
import com.app.viveragro.viveragro2.extras.SlidingTabLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;

import me.drakeet.materialdialog.MaterialDialog;

public class ProdutorConsumidorActivity extends AppCompatActivity implements ValueEventListener,Firebase.CompletionListener{
    private Toolbar mToolbar;
    private Drawer.Result navigationDrawerLeft;
    private AccountHeader.Result headerNavigationLeft;
    //private FloatingActionMenu fab;
    private int mItemDrawerSelected;
    private int mProfileDrawerSelected;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private TextView nomeProdutor,tvDescricao;
    private Produtor produtor;
    private Firebase firebase;
    private float preco;
    private MaterialDialog mMaterialDialog;
    private int cont = 0;
    private Produtor consumidor;
    private ImageView imageView;
    private ImageView ivProdutor;
    private TextView toolbartitle;
    private   Toolbar toolbar;
    private ImageView logo;
    private Carrinho carrinho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtor_perfil);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbartitle = (TextView)findViewById(R.id.toolbartitle);
        consumidor = new Produtor();
        carrinho = new Carrinho();
        consumidor.contextDataDB(this);
        logo = (ImageView)findViewById(R.id.logo);
        logo.setVisibility(View.GONE);
        ivProdutor = (ImageView)findViewById(R.id.ivProfissaoFoto);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle params = intent.getExtras();
            if (params != null) {
                preco +=  params.getFloat("preco");
                cont += params.getInt("cont");
            }
        }



        imageView = (ImageView)findViewById(R.id.imageView3);
        if(preco > 0){
            imageView.setVisibility(View.VISIBLE);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Bundle params = new Bundle();
                params.putString("id",consumidor.getId());
                params.putString("idProd",produtor.getId());
                params.putFloat("preco",preco);
                Intent intent = new Intent(getBaseContext(), CarrinhoActivityProd.class);
                intent.putExtras(params);
                intent.putExtra("produtor",produtor);
                startActivity(intent);
                finish();
                showToast("preco = "+preco );
            }
        });


        produtor = new Produtor();
        nomeProdutor = (TextView)findViewById(R.id.tvProfissionalNome);
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getParcelable("produtor") != null) {
            produtor = getIntent().getExtras().getParcelable("produtor");
        } else {
            Toast.makeText(this, "Fail!", Toast.LENGTH_SHORT).show();
            finish();
        }
//        // TABS
        mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);


        firebase = LibraryClass.getFirebase();
    }
    public void editar(View view){

    }
    @Override
    protected void onResume() {
        super.onResume();
       // produtor.contextDataDB(this);
        tvDescricao = (TextView)findViewById(R.id.tvProfissionalProfissao);
        toolbartitle.setText(produtor.getSitio());

        nomeProdutor.setText(produtor.getNomeProprietario());
        if(produtor.getDescricaoCurta().equals(" ")){
            tvDescricao.setText(produtor.getDescricaoCurta());

        }else{
            tvDescricao.setText(produtor.getDescricaoCurta());
        }
        mViewPager.setAdapter( new TabsAdapter3( getSupportFragmentManager(), this ));
        Glide
                .with(getBaseContext())
                .load(produtor.getFoto())
                .thumbnail(0.5f)
                .centerCrop()
                .placeholder(R.drawable.farmer)
                .crossFade(50)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivProdutor);

//        //mSlidingTabLayout.setDistributeEvenly(true);
//
//        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.corbranca));
//        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.colorFAB));
//        mSlidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.tv_tab);
//        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
//            @Override
//            public void onPageSelected(int position) {
//                //   navigationDrawerLeft.setSelection( position );
//            }
//            @Override
//            public void onPageScrollStateChanged(int state) {}
//        });
//        mSlidingTabLayout.setViewPager( mViewPager );
//        mSlidingTabLayout.setHorizontalFadingEdgeEnabled(true);
//        mSlidingTabLayout.setHorizontalScrollBarEnabled(true);
        //
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if(consumidor.equals(produtor.getId())){
                finish();
            }else{
                carrinho.removePedidoDB(produtor.getId());
                carrinho.removeDB(consumidor.getId()+produtor.getId());
                finish();
            }


        }
        return true;
    }
    //
    public int getQtdProdutos() {

        return produtor.getQtdProdutos();

    }
    public float getPreco() {

        return preco;

    }
    public int getCont() {

        return cont;

    }
    public String getSitio()
    {
        return produtor.getSitio();
    }
    public String getPhone()
    {
        return produtor.getTelefone();
    }
    public String getPropriedade()
    {
        return produtor.getPropriedade();
    }
    public String getEndereco()
    {
        return produtor.getEndereco();
    }
    //
    public Produtor getProdutor() {
        return produtor;
    }
    public String getId() {
        return produtor.getId();
    }
    //
    public String getDescricaoCurta()
    {
        return produtor.getDescricaoCurta();

    }

    public String getDescricaoLonga()
    {
        return produtor.getDescricaoLonga();
    }
    public Produtor getConsumidor(){
        return consumidor;
    }


    protected void showToast( String message ){
        Toast.makeText(this,
                message,
                Toast.LENGTH_LONG)
                .show();
    }

        @Override
        public void onComplete(FirebaseError firebaseError, Firebase firebase) {

        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            try {
                Produtor user2 = dataSnapshot.getValue(Produtor.class);
                consumidor = user2;


                // pegarAnuncio();
            }catch (Exception e){}
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
}
