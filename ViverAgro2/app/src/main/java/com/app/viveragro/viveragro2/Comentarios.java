package com.app.viveragro.viveragro2;

import com.firebase.client.Firebase;

/**
 * Created by Matt on 09/04/2018.
 */

public class Comentarios {


    private String nomeUsuario;
    private String comentarioTitulo;
    private String comentario;
    private String foto;
    private String dataComentario;
    private float voto;

    public Comentarios(String nomeUsuario, String comentario,String titulo){
        this.nomeUsuario = nomeUsuario;
        this.comentario = comentario;
        this.comentarioTitulo = " ";
        this.foto = " ";
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Comentarios(){
        comentarioTitulo = " ";
    }

    public void setVoto(float voto) {
        this.voto = voto;
    }

    public float getVoto() {
        return voto;
    }

    public void setDataComentario(String dataComentario) {
        this.dataComentario = dataComentario;
    }

    public String getDataComentario() {
        return dataComentario;
    }

    public String getComentarioTitulo(){
        return comentarioTitulo;
    }

    public void setComentarioTitulo(String titulo){
        this.comentarioTitulo = titulo;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentraio) {
        this.comentario = comentraio;
    }

    public void saveComentDB(String id,String user){
        Firebase firebase = LibraryClass.getFirebase();
        firebase = firebase.child("comentarios").child(id).child(user);
        firebase.setValue(this);
    }
}
