package com.app.viveragro.viveragro2;

import com.firebase.client.Firebase;

/**
 * Created by Matt on 08/02/2018.
 */

public class Carrinho {
    private String nome;
    private String telefone;
    private String preco;
    private String qtd;
    private String produto;
    private String foto;
    private String descricao;
    private int cont;
    private String endereco;
    private String precofinal;
    private String numero;
    private String rua;
    private String id;
    public Carrinho(){
        nome = " ";
        telefone = " ";
        preco = " ";
        qtd = " ";
        produto = " ";
        foto = " ";
        descricao = " ";
        cont = 0;
        endereco = " ";
        precofinal = " ";
        numero = " ";
        rua = " ";
        id = " ";
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getNumero() {
        return numero;
    }

    public String getPrecofinal() {
        return precofinal;
    }

    public String getRua() {
        return rua;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setPrecofinal(String precofinal) {
        this.precofinal = precofinal;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getCont() {
        return cont;
    }

    public void setCont(int cont) {
        this.cont = cont;
    }

    public String getProduto() {
        return produto;
    }


    public String getQtd() {
        return qtd;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }



    public void setQtd(String qtd) {
        this.qtd = qtd;
    }
    public void removeDB(String id ){
        Firebase firebase = LibraryClass.getFirebase().child("carrinho").child(id);
        firebase.removeValue();
    }
    public void removePedidoDB(String id ){
        Firebase firebase = LibraryClass.getFirebase().child("pedido").child(id);
        firebase.removeValue();

    }
    public void saveDB(String id, int numero){
        Firebase firebase = LibraryClass.getFirebase();
        firebase = firebase.child("carrinho").child(id).child(""+numero);
        firebase.setValue(this);
    }
    public void savePedidoDB(String id, String id2, int numero){
        Firebase firebase = LibraryClass.getFirebase();
        firebase = firebase.child("pedido").child(id).child(id2).child(""+numero);
        firebase.setValue(this);
    }
    public void pagamento(String id,String id2, int numero){
        Firebase firebase = LibraryClass.getFirebase();
        firebase = firebase.child("pagamento").child(id).child(id2).child(""+numero);
        firebase.setValue(this);
    }
}
