package com.app.viveragro.viveragro2;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.app.viveragro.viveragro2.fragments.ProdutoFragment;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matt on 22/11/2017.
 */

public class Produtor implements Parcelable {
    public static String TOKEN = "com.app.viveragro.viveragro2.Produtor.TOKEN";
    public static String ID = "com.app.viveragro.viveragro2.Produtor.ID";
    public static String TIPO = "com.app.viveragro.viveragro2.Produtor.TIPO";
    private String nomeProprietario;
    private String nomePropriedade;
    private String email;
    private String telefone;
    private int qtdProdutos;
    private String senha;
    private int dataEntrada;
    private String foto;
    private String endereco;
    private int entrega;
    private float avaliacao;
    private int qtdAvaliacoes;
    private String propriedade;
    private String id;
    private String descricaoCurta;
    private String descricaoLonga;
    private String sitio;
    private int pedidos;
    private int pedidosPendentes;


    public static final Creator<Produtor> CREATOR = new Creator<Produtor>() {
        @Override
        public Produtor createFromParcel(Parcel in) {
            return new Produtor(in);
        }

        @Override
        public Produtor[] newArray(int size) {
            return new Produtor[size];
        }
    };



    public Produtor(){
        nomeProprietario = " ";
        nomePropriedade = " ";
        foto = " ";
        propriedade = " ";
        endereco = " ";
        nomeProprietario = " ";
        descricaoLonga = " ";
        descricaoCurta = " ";
        sitio = " ";
        senha = " ";
        pedidos = 0;
        pedidosPendentes = 0;
        avaliacao = 0;
        qtdAvaliacoes = 0;
        entrega = 0;
    }


    public int getPedidosPendentes() {
        return pedidosPendentes;
    }

    public void setPedidosPendentes(int pedidosPendentes) {
        this.pedidosPendentes = pedidosPendentes;
    }


    public int getEntrega() {
        return entrega;
    }

    public void setEntrega(int entrega) {
        this.entrega = entrega;
    }

    public void setAvaliacao(float avaliacao) {
        this.avaliacao = avaliacao;
    }

    public int getQtdAvaliacoes() {
        return qtdAvaliacoes;
    }

    public void setQtdAvaliacoes(int qtdAvaliacoes) {
        this.qtdAvaliacoes = qtdAvaliacoes;
    }

    public float getAvaliacao() {
        return avaliacao;
    }

    public int getPedidos() {
        return pedidos;
    }

    public void setPedidos(int pedidos) {
        this.pedidos = pedidos;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }


    public String getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(String propriedade) {
        this.propriedade = propriedade;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setDescricaoLonga(String descricaoLonga) {
        this.descricaoLonga = descricaoLonga;
    }

    public void setDescricaoCurta(String descricaoCurta) {
        this.descricaoCurta = descricaoCurta;
    }

    public String getDescricaoCurta() {
        return descricaoCurta;
    }

    public String getDescricaoLonga() {
        return descricaoLonga;
    }



    public String getSitio() {
        return sitio;
    }

    public void setSitio(String sitio) {
        this.sitio = sitio;
    }


    public void setentregaInMap(Map<String,Object> map){
        if(getNomeProprietario() != null){
            map.put("entrega",getEntrega());
        }else{}
    }
    public void setAvaliacoesInMap(Map<String,Object> map){
        if(getNomeProprietario() != null){
            map.put("avaliacao",getAvaliacao());
        }else{}
    }

    public void setQtdAvaliacoesInMap(Map<String,Object> map){
        if(getNomeProprietario() != null){
            map.put("qtdAvaliacoes",getQtdAvaliacoes());
        }else{}
    }

    public void seteNDERECOInMap(Map<String,Object> map){
        if(getEndereco() != null){
            map.put("endereco",getEndereco());
        }else{}
    }
    public void setSitioInMap(Map<String,Object> map){
        if(getSitio() != null){
            map.put("sitio",getSitio());
        }else{}
    }

    public void setPropriedadeInMap(Map<String,Object> map){
        if(getPropriedade() != null){
            map.put("propriedade",getPropriedade());
        }else{}
    }


    public void setPedidosInMap(Map<String,Object> map){
        if(getNomeProprietario() != null){
            map.put("pedidos",getPedidos());
        }else{}
    }
    public void setPedidosPendentesInMap(Map<String,Object> map){
        if(getNomeProprietario() != null){
            map.put("pedidosPendentes",getPedidosPendentes());
        }else{}
    }

    public void setDescricaoCurtaInMap(Map<String,Object> map){
        if(getNomeProprietario() != null){
            map.put("descricaoCurta",getDescricaoCurta());
        }else{}
    }
    public void setDescricaoLongaInMap(Map<String,Object> map){
        if(getNomeProprietario() != null){
            map.put("descricaoLonga",getDescricaoLonga());
        }else{}
    }

    public void setNomeProprietarioInMap(Map<String,Object> map){
        if(getNomeProprietario() != null){
            map.put("nomeProprietario",getNomeProprietario());
        }else{}
    }
    public void setEmailInMap(Map<String,Object> map){
        if(getNomeProprietario() != null){
            map.put("email",getEmail());
        }else{}
    }
    public void settelefoneInMap(Map<String,Object> map){
        if(getTelefone() != null){
            map.put("telefone",getTelefone());
        }else{}
    }
    public void setSenhaInMap(Map<String,Object> map){
        if(getSenha() != null){
            map.put("senha",getSenha());
        }else{}
    }
    public void setFotoInMap(Map<String,Object> map){
        if(getSenha() != null){
            map.put("foto",getFoto());
        }else{}
    }
    public void setQtdProdutosInMap(Map<String,Object> map){
        if(getEmail() != null){
            map.put("qtdProdutos",getQtdProdutos());
        }else{}
    }

    public void updateTodosProfDB(Firebase.CompletionListener... completionListener){
        Firebase firebase = LibraryClass.getFirebase();
        firebase = firebase.child("produtor").child("Todos os produtores").child(getId());
        Map<String,Object> map = new HashMap<>();
        setSenhaInMap(map);
        setEmailInMap(map);
        setNomeProprietarioInMap(map);
        setQtdProdutosInMap(map);
        settelefoneInMap(map);
        setFotoInMap(map);
        setDescricaoCurtaInMap(map);
        setDescricaoLongaInMap(map);
        seteNDERECOInMap(map);
        setPropriedadeInMap(map);
        setSitioInMap(map);
        setPedidosInMap(map);
        setPedidosPendentesInMap(map);
        setentregaInMap(map);
//        setAvaliacoesInMap(map);
//        setQtdAvaliacoesInMap(map);

        if(map.isEmpty()){
            return;
        }
        if(completionListener != null && completionListener[0] != null){
            firebase.updateChildren(map,completionListener[0]);
        }else{
            firebase.updateChildren(map);
        }
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

    public String getTokenSP(Context context){
        String token = LibraryClass.getSP(context, TOKEN);
        return token;
    }

    public int getQtdProdutos() {
        return qtdProdutos;
    }

    public void setQtdProdutos(int qtdProdutos) {
        this.qtdProdutos = qtdProdutos;
    }

    public String getFoto() {
        return foto;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeProprietario() {
        return nomeProprietario;
    }

    public void setNomeProprietario(String nomeProprietario) {
        this.nomeProprietario = nomeProprietario;
    }

    public String getSenha() {
        return senha;
    }

    public int getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(int dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public String getNomePropriedade() {
        return nomePropriedade;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNomePropriedade(String nomePropriedade) {
        nomePropriedade = nomePropriedade;
    }

    public String getId() {
        return id;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String retrieveTipoSP(Context context){
        return LibraryClass.getSP(context,TIPO);
    }

    public void retrieveIdSP(Context context){
        this.id = LibraryClass.getSP(context,ID);
    }

    public void contextDataDB(Context context){
        retrieveIdSP(context);
        Firebase firebase = LibraryClass.getFirebase();
        firebase = firebase.child("produtor").child("Todos os produtores").child(getId());
        firebase.addListenerForSingleValueEvent((ValueEventListener)context);
    }

    public void saveTodosDB(){
        Firebase firebase = LibraryClass.getFirebase();
        firebase = firebase.child("produtor").child("Todos os produtores").child(getId());
        firebase.setValue(this);

    }

    public void removeDB( ){
        Firebase firebase = LibraryClass.getFirebase().child("produtor").child("Todos os produtores").child(getId());
        firebase.removeValue();

    }

    @Override
    public int describeContents() {
        return 0;
    }
    public Produtor(Parcel parcel) {
        this.nomePropriedade = parcel.readString();
        this.nomeProprietario = parcel.readString();
        this.email = parcel.readString();
        this.telefone = parcel.readString();
        this.qtdProdutos = parcel.readInt();
        this.senha = parcel.readString();
        this.dataEntrada = parcel.readInt();
        this.foto = parcel.readString();
        this.id = parcel.readString();
        this.descricaoCurta = parcel.readString();
        this.descricaoLonga = parcel.readString();
        this.endereco = parcel.readString();
        this.propriedade = parcel.readString();
        this.sitio = parcel.readString();
        this.pedidos = parcel.readInt();
        this.pedidosPendentes = parcel.readInt();
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nomePropriedade);
        parcel.writeString(nomeProprietario);
        parcel.writeString(email);
        parcel.writeString(telefone);
        parcel.writeInt(qtdProdutos);
        parcel.writeString(senha);
        parcel.writeInt(dataEntrada);
        parcel.writeString(foto);
        parcel.writeString(id);
        parcel.writeString(descricaoCurta);
        parcel.writeString(descricaoLonga);
        parcel.writeString(endereco);
        parcel.writeString(propriedade);
        parcel.writeString(sitio);
        parcel.writeInt(pedidos);
        parcel.writeInt(pedidosPendentes);

    }


}
