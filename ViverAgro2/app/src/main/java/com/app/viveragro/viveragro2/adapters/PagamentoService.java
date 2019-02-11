package com.app.viveragro.viveragro2.adapters;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Matt on 17/07/2018.
 */
public interface PagamentoService {

    @FormUrlEncoded
    @POST("wspagamentodireto.rule?sys=EVS")
    Call<Credito> enviardados(@Field("id_autenticacao") int id_autenticacao_dinheiro, @Field("token") String token, @Field("Registros") ArrayList<String> creditoJson);
}