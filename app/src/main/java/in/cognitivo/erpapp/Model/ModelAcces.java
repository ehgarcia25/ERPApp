package in.cognitivo.erpapp.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 29/04/17.
 */

public class ModelAcces {

    private String BASE_URL;
    private Class clase;

    public ModelAcces(String BASE_URL, Class clase) {
        this.BASE_URL = BASE_URL;
        this.clase = clase;
    }

    public ModelAcces() {
        this.BASE_URL = "http://192.168.0.20:8000/";
    }

    protected Object createService(){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        return retrofit.create(clase);
    }


}
