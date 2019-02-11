package com.app.viveragro.viveragro2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;

import java.io.IOException;
import java.util.ArrayList;

public class AdicionarProdutos extends AppCompatActivity  implements ValueEventListener,Firebase.CompletionListener {
    private EditText preco,descricao;
    private Spinner spinner1;
    private Produto produtoC;
    private Produtor produtor;
    private FirebaseStorage storage;
    private int i = 0;
    private Bitmap bitmap;
    private ImageView ivProduto;
    private  byte[] dataByte;
    private Firebase firebase;
    private ProgressDialog progress;
    private ArrayList<String> path = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_adicionar_produtos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Adicione seu produto");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        produtoC = new Produto();
        produtor = new Produtor();
        firebase = LibraryClass.getFirebase();
        storage = FirebaseStorage.getInstance();
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        preco = (EditText) findViewById(R.id.preco);
        descricao = (EditText)findViewById(R.id.edt_descricao) ;
        ivProduto = (ImageView)findViewById(R.id.ivProduto);
        progress = new ProgressDialog(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getParcelable("produtor") != null) {
            produtor = getIntent().getExtras().getParcelable("produtor");
        } else {
            Toast.makeText(this, "Fail!", Toast.LENGTH_SHORT).show();
            finish();
        }
        produtor.contextDataDB(this);

        Log.i("LOG", "/////////Nome3: " + produtor.getEmail());
        Log.i("LOG", "/////////Nome:4 " + produtor.getNomeProprietario());

    }

    public void addFoto(View view){
        FishBun.with(AdicionarProdutos.this)
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
                        for (i = 0; i < n; i++) {
                            try {
                                int rotacao = getRotationForImage(path.get(i));
                                bitmap = BitmapFactory.decodeFile(path.get(i));
                                bitmap = rotateBitmap(bitmap, rotacao);
                                dataByte = BinaryBytes.getBitmapInBytes(bitmap);
                                bitmap = decodeFile(dataByte);
                                bitmap = resizeImage(this, bitmap, 200, 200);
                            } catch (Exception e) {
                                showToast("Aconteceu um problema ao tentar enviar sua imagem");
                                Intent intent = new Intent(getBaseContext(), ProdutorActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            //Bitmap resized = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
                            dataByte = BinaryBytes.getBitmapInBytes(bitmap);
                            //String nome = BinaryBytes.getResourceName(this,R.drawable.camaro);
                            StorageReference storageRef = storage.getReferenceFromUrl("gs://nosso-chate-fb.appspot.com");
                            // Create a reference to "mountains.jpg"
                            String corte = path.get(i).replace("/", "");
                            StorageReference mountainsRef = storageRef.child(produtor.getId() + "/" + produtor.getNomeProprietario() + produtor.getQtdProdutos()+1);
                            // Create a reference to 'images/mountains.jpg'
                            StorageReference mountainImagesRef = storageRef.child(produtor.getId() + "/" + produtor.getNomeProprietario() + produtor.getQtdProdutos()+1);
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
                                    produtoC.setUrl(downloadUrl.toString());
                                    ivProduto.setVisibility(View.VISIBLE);
                                    Glide
                                            .with(getBaseContext())
                                            .load(produtoC.getUrl())
                                            .thumbnail(0.5f)
                                            .centerCrop()
                                            .placeholder(R.drawable.placeholde)
                                            .crossFade(50)
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .into(ivProduto);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_remover) {
            String descricaoS = descricao.getText().toString();
            String precoS = preco.getText().toString();;
            String produto = "1";
            if (String.valueOf(spinner1.getSelectedItem()).equals("Nenhum produto") && precoS.trim().isEmpty()) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setMessage("Há dados em brancos");
                dlg.setNeutralButton("Ok",null);
                dlg.show();
            }else{
                if(produtoC.getUrl() == null){
                    AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                    dlg.setMessage("Há dados em brancos");
                    dlg.setNeutralButton("Ok",null);
                    dlg.show();
                }else{
                    if(precoS.trim().isEmpty()){
                        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                        dlg.setMessage("Há dados em brancos");
                        dlg.setNeutralButton("Ok",null);
                        dlg.show();
                    }else{
                        if(descricaoS.trim().isEmpty()){
                            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                            dlg.setMessage("Há dados em brancos");
                            dlg.setNeutralButton("Ok",null);
                            dlg.show();
                        }else{
                            produto = String.valueOf(spinner1.getSelectedItem());
                            //showToast("Produto: "+produto+"\npreco: "+precoS);
                            produtoC.setPreco(precoS);
                            produtoC.setProduto(produto);
                            produtoC.setDescricao(descricaoS);
                            produtoC.setCont(produtor.getQtdProdutos()+1);
                            produtoC.saveTodosDB(produtor.getId(),produtor.getQtdProdutos()+1);
                            produtor.setQtdProdutos(produtor.getQtdProdutos()+1);
                            produtor.updateTodosProfDB(this);
                            Intent intent = new Intent(this, ProdutorPerfil.class);
                            intent.putExtra("produtor",produtor);
                            startActivity(intent);
                        }
                    }
                }
            }
        }
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    public void cancelar(View view){
        finish();
    }
    protected void showToast(String message) {
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
        Produtor u = dataSnapshot.getValue(Produtor.class);
        produtor = u;

    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
}
