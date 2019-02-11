package com.app.viveragro.viveragro2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by Matt on 17/02/2018.
 */

public class BinaryBytes {
    public static byte[] getBitmapInBytes(  Bitmap img ){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return( byteArray );
    }
    public static byte[] getResourceInBytes(Context context, int resource ){
        final Bitmap img = BitmapFactory.decodeResource( context.getResources(), resource );
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return( byteArray );
    }

    public static String getResourceName( Context context, int resource ){
        return( context.getResources().getResourceEntryName(resource) );

    }

    // Decodifica a imagem e escala para a redução do consumo de memória
    private static  Bitmap decodeFile(byte[] f) {
        try {
            // Decodifica o tamanho da imagem
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            //BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            BitmapFactory.decodeByteArray(f,0,f.length,o);

            // O novo tamanho que queremos
            final int REQUIRED_SIZE=70;

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
}
