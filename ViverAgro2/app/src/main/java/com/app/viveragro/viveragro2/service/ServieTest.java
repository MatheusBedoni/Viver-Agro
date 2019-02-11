package com.app.viveragro.viveragro2.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.app.viveragro.viveragro2.Produto;
import com.app.viveragro.viveragro2.Produtor;
import com.app.viveragro.viveragro2.ProdutorPerfil;
import com.app.viveragro.viveragro2.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by Matt on 05/03/2018.
 */

public class ServieTest extends Service implements ValueEventListener,Firebase.CompletionListener{
    private Produtor produtor;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        produtor = new Produtor();
    }
    //
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        try{
            produtor.contextDataDB(this);
        }catch (Exception e){}
        //
        return(super.onStartCommand(intent, flags, startId));
    }
    @Override
    public void onComplete(FirebaseError firebaseError, Firebase firebase) {

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        try {
            Produtor u = dataSnapshot.getValue(Produtor.class);
            if(u.getPedidos() != u.getPedidosPendentes()){
                gerarNotificacaoCon();

            }
            Log.i("LOG", "produtor: " + u.getNomeProprietario());
        }catch (Exception e){

        }
    }
    public void gerarNotificacaoCon(){
        //
        Intent intent = new Intent(getBaseContext(),ProdutorPerfil.class);
        NotificationManager nm = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
        //
        builder.setTicker("");
        builder.setContentTitle("Parabéns, você recebeu um novo pedido de compra no seu perfil.");
        builder.setContentText("");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getBaseContext().getResources(), R.mipmap.ic_launcher));
        builder.setContentIntent(p);
        //
        Notification n = builder.build();
        n.vibrate = new long[]{150, 300, 150, 600};
        n.flags = Notification.FLAG_AUTO_CANCEL;
        nm.notify(R.mipmap.ic_launcher, n);
        //

        //
        try{
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(getBaseContext(), som);
            toque.play();
        }
        //
        catch(Exception e){}
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
}
