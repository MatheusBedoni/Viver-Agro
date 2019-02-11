package com.app.viveragro.viveragro2;

import android.content.Context;
import android.content.SharedPreferences;

import com.firebase.client.Firebase;

/**
 * Created by Matt on 02/12/2017.
 */

public class LibraryClass {
    public static String PREF = "com.app.viveragro.viveragro2.PREF";
    private static Firebase firebase;


    private LibraryClass(){}

    public static Firebase getFirebase(){
        try{
            if( firebase == null ){
                firebase = new Firebase("https://nosso-chate-fb.firebaseio.com//");
            }
            return( firebase );
        }catch (Exception e){

        }
        return null;
    }
    public static String getStorage(){

        return("gs://nosso-chate-fb.appspot.com" );
    }

    static public void saveSP(Context context, String key, String value ){
        SharedPreferences sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    static public String getSP(Context context, String key ){
        SharedPreferences sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        String token = sp.getString(key, "");
        return( token );
    }
}
