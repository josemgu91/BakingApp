package com.josemgu91.bakingapp.data.remote;

import com.josemgu91.bakingapp.data.remote.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by jose on 2/15/18.
 */

public interface BakingServerRetrofitApi {

    @GET("")
    Call<List<Recipe>> getRecipes();

}
