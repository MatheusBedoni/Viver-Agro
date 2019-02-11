package com.app.viveragro.viveragro2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import com.app.viveragro.viveragro2.extras.SlidingTabLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;

import java.io.IOException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Calendar;

public class ProdutorPerfil extends AppCompatActivity implements ValueEventListener,Firebase.CompletionListener{
    private Toolbar mToolbar;
    private Drawer.Result navigationDrawerLeft;
    private AccountHeader.Result headerNavigationLeft;
    //private FloatingActionMenu fab;
    private int mItemDrawerSelected;
    private FirebaseStorage storage;
    private int mProfileDrawerSelected;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private Bitmap bitmap;
    private ImageView fotoUsuario;
    private TextView nomeProdutor,tvDescricao;
    private Produtor produtor;
    private Firebase firebase;
    private int i = 0;
    private ArrayList<String> path = new ArrayList<>();
    private ImageView ivProdutor,logo;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private View navHeader;
    private DrawerLayout drawer;
    private TextView nomeUsuario, emailUsuario;
    private Toolbar toolbar;
    private boolean alarmeAtivo;
    private TextView toolbartitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtor_perfil);
       toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        produtor = new Produtor();
        logo = (ImageView)findViewById(R.id.logo);
        nomeProdutor = (TextView)findViewById(R.id.tvProfissionalNome);
        storage = FirebaseStorage.getInstance();
        ivProdutor = (ImageView) findViewById(R.id.ivProfissaoFoto);
        // TABS
        toolbartitle = (TextView)findViewById(R.id.toolbartitle);
        mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);

        firebase = LibraryClass.getFirebase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            produtor.contextDataDB(this);
        }catch (Exception e){
            Intent intent = new Intent(getBaseContext(), Entrar.class);
            startActivity(intent);
        }

        alarmeAtivo = (PendingIntent.getBroadcast(this, 0, new Intent("ALARME_DISPARADO"), PendingIntent.FLAG_NO_CREATE) == null);
        if(alarmeAtivo){
            Intent intent = getIntent();
            intent = new Intent("ALARME_DISPARADO");
            PendingIntent p = PendingIntent.getBroadcast(this, 0, intent, 0);
            //
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            c.add(Calendar.SECOND, 3);
            //
            AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarme.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 5000, p);
        }
        tvDescricao = (TextView)findViewById(R.id.tvProfissionalProfissao);

        ivProdutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FishBun.with(ProdutorPerfil.this)
                        .setPickerCount(1)
                        .setPickerSpanCount(3)
                        .setActionBarColor(Color.parseColor("#4CAF50"), Color.parseColor("#E64A19"))
                        .textOnImagesSelectionLimitReached("Limit Reached!")
                        .textOnNothingSelected("Nothing Selected")
                        .setArrayPaths(path)
                        .setCamera(true)
                        .setButtonInAlbumActiviy(true)
                        .setReachLimitAutomaticClose(true)
                        .startAlbum();
            }
        });
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),ProdtorConsumidor.class);
                intent.putExtra("produtor",produtor);
                startActivity(intent);

            }
        });


    }
    public void editar(View view){
        Intent intent = new Intent(this, AdicionarDescricao.class);
        intent.putExtra("produtor",produtor);
        startActivity(intent);
    }
    //
    private Bitmap decodeFile(byte[] f) {
        try {
            // Decodifica o tamanho da imagem
            int REQUIRED_SIZE = 0;
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            //BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            BitmapFactory.decodeByteArray(f,0,f.length,o);
            // O novo tamanho que queremos
            REQUIRED_SIZE = 200;
            // Achar o valor correto para a escala
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }
            // Decodifica com o inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return  BitmapFactory.decodeByteArray(f,0,f.length,o2);
        } catch (Exception e) {}
        return null;
    }
    //
    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        try{
            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_NORMAL:
                    return bitmap;
                case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                    matrix.setScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.setRotate(180);
                    break;
                case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                    matrix.setRotate(180);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_TRANSPOSE:
                    matrix.setRotate(90);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.setRotate(90);
                    break;
                case ExifInterface.ORIENTATION_TRANSVERSE:
                    matrix.setRotate(-90);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.setRotate(-90);
                    break;
                default:
                    return bitmap;
            }
            try {
                Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                bitmap.recycle();
                return bmRotated;
            }
            catch (OutOfMemoryError e) {
                e.printStackTrace();
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //
    public int getRotationForImage(String path) {
        int rotation = 0;
        try {
            ExifInterface exif = new ExifInterface(path);
            rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotation;
    }
    //
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageData) {
        super.onActivityResult(requestCode, resultCode, imageData);
        switch (requestCode) {
            case Define.ALBUM_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                        path = imageData.getStringArrayListExtra(Define.INTENT_PATH);
                        int n = path.size();
                        byte[] dataByte;
                        for (i = 0; i < n; i++) {
                            try {
                                int rotacao = getRotationForImage(path.get(i));
                                bitmap = BitmapFactory.decodeFile(path.get(i));
                                bitmap = rotateBitmap(bitmap, rotacao);
                                dataByte = BinaryBytes.getBitmapInBytes(bitmap);
                                bitmap = decodeFile(dataByte);
                            } catch (Exception e) {
                                showToast("Aconteceu um problema ao tentar enviar sua imagem");
                                Intent intent = new Intent(getBaseContext(), ProdutorActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            bitmap = resizeImage(this, bitmap, 200, 200);
                            //Bitmap resized = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
                            dataByte = BinaryBytes.getBitmapInBytes(bitmap);
                            //String nome = BinaryBytes.getResourceName(this,R.drawable.camaro);
                            StorageReference storageRef = storage.getReferenceFromUrl("gs://nosso-chate-fb.appspot.com");
                            // Create a reference to "mountains.jpg"
                            String corte = path.get(i).replace("/", "");
                            StorageReference mountainsRef = storageRef.child(produtor.getId() + "/" + produtor.getNomeProprietario() + 1);
                            // Create a reference to 'images/mountains.jpg'
                            StorageReference mountainImagesRef = storageRef.child(produtor.getId() + "/" + produtor.getNomeProprietario() + 1);
                            // While the file names are the same, the references point to different files
                            mountainsRef.getName().equals(mountainImagesRef.getName());    // true
                            mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false
                            UploadTask uploadTask = mountainsRef.putBytes(dataByte);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    showToast("Aconteceu um problema ao tentar enviar sua imagem");
                                    Intent intent = new Intent(getBaseContext(), ProdutorPerfil.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    produtor.setFoto(downloadUrl.toString());
                                    ivProdutor.setVisibility(View.VISIBLE);
                                    Glide
                                            .with(getBaseContext())
                                            .load(produtor.getFoto())
                                            .thumbnail(0.5f)
                                            .centerCrop()
                                            .placeholder(R.drawable.farmer)
                                            .crossFade(50)
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .into(ivProdutor);
                                    atualizar();
                                    showToast("Sua imagem foi salva");

                                    // Get the data from an ImageView as bytes
                                }
                            });
                        }


                }
                    break;
        }
    }
    //
    private static Bitmap resizeImage(Context context, Bitmap bmpOriginal,
                                      float newWidth, float newHeight) {
        Bitmap novoBmp = null;
        int w = bmpOriginal.getWidth();
        int h = bmpOriginal.getHeight();
        float densityFactor = context.getResources().getDisplayMetrics().density;
        float novoW = newWidth * densityFactor;
        float novoH = newHeight * densityFactor;
        //Calcula escala em percentagem do tamanho original para o novo tamanho
        float scalaW = novoW / w;
        float scalaH = novoH / h;
        // Criando uma matrix para manipulação da imagem BitMap
        Matrix matrix = new Matrix();
        // Definindo a proporção da escala para o matrix
        matrix.postScale(scalaW, scalaH);
        //criando o novo BitMap com o novo tamanho
        novoBmp = Bitmap.createBitmap(bmpOriginal, 0, 0, w, h, matrix, true);
        return novoBmp;
    }
    //
    public int getQtdProdutos() {

            return produtor.getQtdProdutos();

    }
    public void atualizar(){
        produtor.updateTodosProfDB(this);
    }
    //
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sair) {
            try{
                firebase.unauth();
                Intent intent = new Intent(getBaseContext(),Entrar.class);
                startActivity(intent);
                finish();
            }catch(Exception e){

            }

        }
        if (id == R.id.action_list) {
            try{
                firebase.unauth();
                Intent intent = new Intent(getBaseContext(),PedidoActivity.class);
                intent.putExtra("produtor",produtor);
                startActivity(intent);
            }catch(Exception e){

            }

        }
        if (id == R.id.action_remover) {

            firebase.removeUser(produtor.getEmail(), produtor.getSenha(), new Firebase.ResultHandler() {
                @Override
                public void onSuccess() {
                    produtor.removeDB();
                    showToast("Sua conta foi removida");
                    Intent intent = new Intent(getBaseContext(),Entrar.class);
                    startActivity(intent);
                }
                @Override
                public void onError(FirebaseError firebaseError) {
                    showToast("Algo de errado aconteceu");
                }
            });

            finish();
        }
//        if (id == R.id.action_share) {
//            Intent intent = new Intent(getBaseContext(),DestaqueActivity.class);
//            intent.putExtra("profissional",profissional);
//            startActivity(intent);
//        }
        return super.onOptionsItemSelected(item);
    }
    protected void showToast( String message ) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_LONG)
                .show();
    }
    @Override
    public void onComplete(FirebaseError firebaseError, Firebase firebase) {}
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Produtor u = dataSnapshot.getValue(Produtor.class);
        produtor = u;
        try {
           toolbartitle.setText(produtor.getSitio());
            nomeProdutor.setText(produtor.getNomeProprietario());
            if (produtor.getDescricaoCurta().equals(" ")) {

            } else {
                tvDescricao.setText(produtor.getDescricaoCurta());
            }
            mViewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(), this));

            Glide
                    .with(getBaseContext())
                    .load(produtor.getFoto())
                    .thumbnail(0.5f)
                    .centerCrop()
                    .placeholder(R.drawable.prr)
                    .crossFade(50)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivProdutor);

            if(produtor.getPedidos() != produtor.getPedidosPendentes()){
                produtor.setPedidos(produtor.getPedidosPendentes());
                atualizar();
                Intent intent = new Intent(getBaseContext(), PedidoActivity.class);
                intent.putExtra("produtor",produtor);
                startActivity(intent);
            }
        }
        catch (Exception e){
            Intent intent = new Intent(getBaseContext(), Entrar.class);
            startActivity(intent);
        }
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }


}
