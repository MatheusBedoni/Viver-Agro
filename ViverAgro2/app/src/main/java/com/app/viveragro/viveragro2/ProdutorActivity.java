package com.app.viveragro.viveragro2;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.viveragro.viveragro2.adapters.TabsAdapter;
import com.app.viveragro.viveragro2.adapters.TabsAdapter2;
import com.app.viveragro.viveragro2.extras.SlidingTabLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;

import java.util.GregorianCalendar;

import me.drakeet.materialdialog.MaterialDialog;

public class ProdutorActivity extends AppCompatActivity implements ValueEventListener,Firebase.CompletionListener{
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
    private Consumidor consumidor;
    private ImageView imageView;
    private ImageView ivProdutor;
    private TextView toolbartitle;
    private   Toolbar toolbar;
    private ImageView logo;
    private TextView aval;
    private Carrinho carrinho;
    private int dataInt;
    private LinearLayout principal;
    private FrameLayout frame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtor_perfil);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        frame = (FrameLayout)findViewById(R.id.frame);
        principal = (LinearLayout)findViewById(R.id.principal);
        consumidor = new Consumidor();
        try{
            consumidor.contextDataDB(this);
        }catch (Exception e){

        }
        carrinho = new Carrinho();

        aval = (TextView)findViewById(R.id.aval);
        logo = (ImageView)findViewById(R.id.logo);
        logo.setVisibility(View.GONE);
        toolbartitle = (TextView)findViewById(R.id.toolbartitle);
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
                Intent intent = new Intent(getBaseContext(), CarrinhoActivity.class);
                intent.putExtras(params);
                intent.putExtra("consumidor",consumidor);
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
        // TABS
        mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);


        firebase = LibraryClass.getFirebase();
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
            if (produtor.getDescricaoCurta().length() > 45) {
                String descricaoCortada = produtor.getDescricaoCurta().substring(0, 45);
                tvDescricao.setText(descricaoCortada + "...");
            } else
                tvDescricao.setText(produtor.getDescricaoCurta());
        }
        mViewPager.setAdapter( new TabsAdapter2( getSupportFragmentManager(), this ));
        Glide
                .with(getBaseContext())
                .load(produtor.getFoto())
                .thumbnail(0.5f)
                .centerCrop()
                .placeholder(R.drawable.farmer)
                .crossFade(50)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivProdutor);

        //mSlidingTabLayout.setDistributeEvenly(true);

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
//        pedido.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            frame.setVisibility(View.VISIBLE);
//            //principal.setVisibility(View.VISIBLE);
//            }
//        });

    }

    //
    public int getQtdProdutos() {

        return produtor.getQtdProdutos();

    }
    public void editar(View view){

    }
    public void enviarComent(View view){
        float avaliacao = 0;
        //
        Comentarios comentarios = new Comentarios();
        RatingBar rating = (RatingBar)findViewById(R.id.ratingBar);
        avaliacao = rating.getRating();
        //
        EditText titulo = (EditText)findViewById(R.id.titulo);
        EditText comentario = (EditText)findViewById(R.id.comentario);
        //
        GregorianCalendar gc = new GregorianCalendar();
        String mes, dia;
        if (gc.get(gc.MONTH) >= 0 && gc.get(gc.MONTH) <= 9) {
            int mesint = gc.get(gc.MONTH) + 1;
            mes = "0" + mesint;
        } else {
            int mesint = gc.get(gc.MONTH) + 1;
            mes = "" + mesint;
        }
        if (gc.get(gc.DAY_OF_MONTH) >= 1 && gc.get(gc.DAY_OF_MONTH) < 10) {
            dia = "0" + gc.get(gc.DAY_OF_MONTH);
        } else {
            dia = "" + gc.get(gc.DAY_OF_MONTH);
        }
        String data = "" + gc.get(gc.YEAR) + "" + mes + "" + dia;
        dataInt = Integer.parseInt(data);
        dataInt *= -1;
        //

        if(gc.get(gc.MONTH ) >= 1 && gc.get(gc.MONTH ) < 9){
            int mesint = gc.get(gc.MONTH )+1;
            mes = "0"+mesint;
        }else{
            int mesint = gc.get(gc.MONTH )+1;
            mes = ""+mesint;
        }
        if(gc.get(gc.DAY_OF_MONTH ) >= 1 && gc.get(gc.DAY_OF_MONTH ) < 10){
            dia = "0"+gc.get(gc.DAY_OF_MONTH );
        }else{
            dia = ""+gc.get(gc.DAY_OF_MONTH );
        }

        comentarios.setNomeUsuario(consumidor.getNome());
        comentarios.setComentario(titulo.getText().toString());
        comentarios.setComentarioTitulo(comentario.getText().toString());
        comentarios.setVoto(avaliacao);
        comentarios.setFoto("");
        comentarios.setDataComentario(dia+"/"+mes+"/"+gc.get(gc.YEAR));
        comentarios.saveComentDB(produtor.getId(),consumidor.getNome());
        //
        FrameLayout frame = (FrameLayout)findViewById(R.id.frame);
        frame.setVisibility(View.GONE);
        comentario.setText(" ");
        titulo.setText(" ");
        rating.setRating((float)0.0);
        //comentarios.saveComentDB(profissional.getId(),user.getNome());
        //
       // produtor.setQtdAvaliaçoesNovas(produtor.getQtdAvaliacoes()+1);
        //produtor.setVotos(avaliacao);
        produtor.updateTodosProfDB(this);

        //


        showToast("Obrigado pelo seu comentario");
        frame.setVisibility(View.GONE);


    }

    public float getPreco() {

        return preco;

    }
    public int getCont() {

        return cont;

    }
    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sair) {

            firebase.unauth();
            Intent intent = new Intent(getBaseContext(),Entrar.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.action_remover) {


        }
        if (id == android.R.id.home) {
            finish();
            carrinho.removePedidoDB(produtor.getId());
            carrinho.removeDB(consumidor.getId()+produtor.getId());

        }

        return super.onOptionsItemSelected(item);
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
    public Consumidor getConsumidor(){
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
                Consumidor user2 = dataSnapshot.getValue(Consumidor.class);
                consumidor = user2;

                // pegarAnuncio();
            }catch (Exception e){}
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
}
