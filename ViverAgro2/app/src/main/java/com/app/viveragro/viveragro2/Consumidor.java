package com.app.viveragro.viveragro2;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

/**
 * Created by Matt on 22/11/2017.
 */

public class Consumidor implements Parcelable {
    public static String TOKEN = "com.app.viveragro.viveragro2.Consumidor.TOKEN";
    public static String ID = "com.app.viveragro.viveragro2.Consumidor.ID";
    public static String TIPO = "com.app.viveragro.viveragro2.Consumidor.TIPO";
    private String nome;
    private String telefone;
    private String email;
    private String senha;
    private String id;
    private int dataEntrada;


    protected Consumidor(Parcel in) {
        nome = in.readString();
        telefone = in.readString();
        email = in.readString();
        senha = in.readString();
        id = in.readString();
        dataEntrada = in.readInt();
    }
    public Consumidor(){}

    public static final Creator<Consumidor> CREATOR = new Creator<Consumidor>() {
        @Override
        public Consumidor createFromParcel(Parcel in) {
            return new Consumidor(in);
        }

        @Override
        public Consumidor[] newArray(int size) {
            return new Consumidor[size];
        }
    };

    public int getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(int dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    public void saveTokenSP(Context context, String token){
        LibraryClass.saveSP(context,TOKEN,token);
    }
    public void saveTipoSP(Context context,String token){
        LibraryClass.saveSP(context,TIPO,token);
    }
    public void saveIdSP(Context context, String token){
        LibraryClass.saveSP(context,ID,token);
    }

    public void retrieveIdSP(Context context){
        this.id = LibraryClass.getSP(context,ID);
    }

    public String getTokenSP(Context context){
        String token = LibraryClass.getSP(context, TOKEN);
        return token;
    }
    public void contextDataDB(Context context){
        retrieveIdSP(context);
        Firebase firebase = LibraryClass.getFirebase();
        firebase = firebase.child("consumidor").child("Todos os consumidores").child(getId());
        firebase.addListenerForSingleValueEvent((ValueEventListener)context);
    }

    public void saveTodosDB(){
        Firebase firebase = LibraryClass.getFirebase();
        firebase = firebase.child("consumidor").child("Todos os consumidores").child(getId());
        firebase.setValue(this);
    }
    public void removeDB( ){
        Firebase firebase = LibraryClass.getFirebase().child("consumidor").child("Todos os consumidores").child(getId());
        firebase.removeValue();

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nome);
        parcel.writeString(telefone);
        parcel.writeString(email);
        parcel.writeString(senha);
        parcel.writeString(id);
        parcel.writeInt(dataEntrada);
    }
}
