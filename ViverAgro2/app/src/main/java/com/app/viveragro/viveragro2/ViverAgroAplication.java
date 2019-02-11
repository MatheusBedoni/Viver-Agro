package com.app.viveragro.viveragro2;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Matt on 06/12/2017.
 */

public class ViverAgroAplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}