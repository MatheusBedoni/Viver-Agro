package com.app.viveragro.viveragro2;

import android.os.Parcel;
import android.os.Parcelable;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matt on 31/12/2017.
 */

public class Produto implements Parcelable {
    private String produto;
    private String preco;
    private String url;
    private String descricao;
    private int cont;
    private String id;
    public Produto(){}

    public static final Creator<Produto> CREATOR = new Creator<Produto>() {
        @Override
        public Produto createFromParcel(Parcel in) {
            return new Produto(in);
        }

        @Override
        public Produto[] newArray(int size) {
            return new Produto[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCont() {
        return cont;
    }

    public String getUrl() {
        return url;
    }

    public void setCont(int cont) {
        this.cont = cont;
    }



    public void setUrl(String url) {
        this.url = url;
    }

    public String getPreco() {
        return preco;
    }

    public String getProduto() {
        return produto;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public void setContInMap(Map<String,Object> map){
        if(getProduto() != null){
            map.put("cont",getCont());
        }else{}
    }
    public void setDescricaoInMap(Map<String,Object> map){
        if(getDescricao() != null){
            map.put("descricao",getDescricao());
        }else{}
    }

    public void setIdInMap(Map<String,Object> map){
        if(getId() != null){
            map.put("id",getId());
        }else{}
    }

    public void setUrlInMap(Map<String,Object> map){
        if(getUrl() != null){
            map.put("url",getUrl());
        }else{}
    }

    public void setPrecoInMap(Map<String,Object> map){
        if(getPreco() != null){
            map.put("preco",getPreco());
        }else{}
    }


    public void setProdutoInMap(Map<String,Object> map){
        if(getProduto() != null){
            map.put("produto",getProduto());
        }else{}
    }

    public void updateTodosProfDB(Firebase.CompletionListener... completionListener){
        Firebase firebase = LibraryClass.getFirebase();
        firebase = firebase.child("produto").child(getId()).child(""+getCont());
        Map<String,Object> map = new HashMap<>();
       setContInMap(map);
       setPrecoInMap(map);
       setProdutoInMap(map);
       setUrlInMap(map);
       setIdInMap(map);
       setDescricaoInMap(map);
        if(map.isEmpty()){
            return;
        }
        if(completionListener != null && completionListener[0] != null){
            firebase.updateChildren(map,completionListener[0]);
        }else{
            firebase.updateChildren(map);
        }
    }


    public void setProduto(String produto) {
        this.produto = produto;
    }
    public void saveTodosDB(String id, int numero){
        Firebase firebase = LibraryClass.getFirebase();
        firebase = firebase.child("produto").child(id).child(""+numero);
        firebase.setValue(this);
    }
    public void removeDB(String id ){
        Firebase firebase = LibraryClass.getFirebase();
        firebase = firebase.child("produto").child(id).child(""+getCont());
        firebase.removeValue();
    }


    @Override
    public int describeContents() {
        return 0;
    }
    public Produto(Parcel parcel) {
        produto = parcel.readString();
        preco = parcel.readString();
        url = parcel.readString();
        descricao = parcel.readString();
        cont = parcel.readInt();
        id = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(produto);
        parcel.writeString(preco);
        parcel.writeString(url);
        parcel.writeString(descricao);
        parcel.writeInt(cont);
        parcel.writeString(id);
    }
}
