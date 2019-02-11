package com.app.viveragro.viveragro2.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Matt on 06/03/2018.
 */

public class BroadcastReceiverAux extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, ServieTest.class));
    }
}
