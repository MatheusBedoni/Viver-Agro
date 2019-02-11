package com.app.viveragro.viveragro2.adapters;

import com.app.viveragro.viveragro2.LibraryClass;
import com.firebase.client.Firebase;

/**
 * Created by Matt on 11/08/2018.
 */

public class Adm {
    private String telefonecomprador;
    private String telefonevendedor;
    private String precototal;
    private String data;
    private String numero;
    private String rua;
    private String bairro;
    private String id;

    public Adm(){
       telefonecomprador = " ";
       telefonevendedor = " ";
       precototal = " ";
        numero = " ";
        rua = " ";
        id = " ";
        bairro = " ";
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getData() {
        return data;
    }

    public String getNumero() {
        return numero;
    }

    public String getId() {
        return id;
    }

    public String getPrecototal() {
        return precototal;
    }

    public String getRua() {
        return rua;
    }

    public String getTelefonecomprador() {
        return telefonecomprador;
    }

    public String getTelefonevendedor() {
        return telefonevendedor;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setPrecototal(String precototal) {
        this.precototal = precototal;
    }

    public void setTelefonecomprador(String telefonecomprador) {
        this.telefonecomprador = telefonecomprador;
    }

    public void setTelefonevendedor(String telefonevendedor) {
        this.telefonevendedor = telefonevendedor;
    }
    public void savePedidoDB(String id){
        Firebase firebase = LibraryClass.getFirebase();
        firebase = firebase.child("adm").child(id);
        firebase.setValue(this);
    }
}
