package com.app.viveragro.viveragro2;

import android.content.Context;

import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

/**
 * Created by Matt on 22/11/2017.
 */

public class Fretista {
    public static String TOKEN = "com.app.viveragro.viveragro2.Fretista.TOKEN";
    public static String ID = "com.app.viveragro.viveragro2.Fretista.ID";
    public static String TIPO = "com.app.viveragro.viveragro2.Fretista.TIPO";
    private String nome;
    private String telefone;
    private String email;
    private String carro;
    private String habilitacao;
    private String foto;
    private String descricao;
    private int dataEntrada;
    private String id;
    private String senha;

    Fretista(){
        descricao = " ";
        habilitacao = " ";
        carro = " ";
        foto = " ";
        telefone = " ";
        email = " ";
        nome = " ";

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
    public void contextDataDB(Context context){
        retrieveIdSP(context);
        Firebase firebase = LibraryClass.getFirebase();
        firebase = firebase.child("fretista").child("Todos os fretistas").child(getId());
        firebase.addListenerForSingleValueEvent((ValueEventListener)context);
    }
    public void retrieveIdSP(Context context){
        this.id = LibraryClass.getSP(context,ID);
    }
    public String getTokenSP(Context context){
        String token = LibraryClass.getSP(context, TOKEN);
        return token;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(int dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCarro() {
        return carro;
    }

    public String getHabilitacao() {
        return habilitacao;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setCarro(String carro) {
        this.carro = carro;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setHabilitacao(String habilitacao) {
        this.habilitacao = habilitacao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void saveTodosDB(){
        Firebase firebase = LibraryClass.getFirebase();
        firebase = firebase.child("fretista").child("Todos os fretistas").child(getId());
        firebase.setValue(this);

    }
}
