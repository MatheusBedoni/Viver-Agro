package com.app.viveragro.viveragro2;

import com.app.viveragro.viveragro2.adapters.Credito;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Matt on 18/07/2018.
 */

public class CreditoDes  implements JsonDeserializer<Object> {
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement credito = json.getAsJsonObject();

        if( json.getAsJsonObject().get("credito") != null ){
            credito = json.getAsJsonObject().get("credito");
        }

        return ( new Gson().fromJson( credito, Credito.class ));
    }
}