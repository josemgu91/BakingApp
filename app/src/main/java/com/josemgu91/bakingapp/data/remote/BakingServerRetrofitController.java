package com.josemgu91.bakingapp.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 2/15/18.
 */

public class BakingServerRetrofitController {

    private final static String REMOTE_SERVER_URI = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    public BakingServerRetrofitApi createRetrofitClient() {
        final Gson gson = new GsonBuilder()
                .create();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(REMOTE_SERVER_URI)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(BakingServerRetrofitApi.class);
    }

}
