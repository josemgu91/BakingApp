/*
 * MIT License
 *
 * Copyright (c) 2018 José Miguel García Urrutia.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.josemgu91.bakingapp.data.remote;

import com.josemgu91.bakingapp.domain.datagateways.DataGatewayException;
import com.josemgu91.bakingapp.domain.datagateways.IngredientDataGateway;
import com.josemgu91.bakingapp.domain.datagateways.RecipeDataGateway;
import com.josemgu91.bakingapp.domain.datagateways.StepDataGateway;
import com.josemgu91.bakingapp.domain.entities.Ingredient;
import com.josemgu91.bakingapp.domain.entities.Recipe;
import com.josemgu91.bakingapp.domain.entities.Step;
import com.josemgu91.bakingapp.domain.util.ListMapper;
import com.josemgu91.bakingapp.domain.util.OutputMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by jose on 2/15/18.
 */

public class RemoteRetrofitRepository implements RecipeDataGateway, StepDataGateway, IngredientDataGateway {

    private final boolean cacheServerResponse;

    private final BakingServerRetrofitApi bakingServerRetrofitApi;
    private final ListMapper<com.josemgu91.bakingapp.data.remote.model.Recipe, Recipe> recipeListMapper;
    private final ListMapper<com.josemgu91.bakingapp.data.remote.model.Ingredient, Ingredient> ingredientListMapper;
    private final ListMapper<com.josemgu91.bakingapp.data.remote.model.Step, Step> stepListMapper;

    private List<com.josemgu91.bakingapp.data.remote.model.Recipe> cachedResponse;

    public RemoteRetrofitRepository(final boolean cacheServerResponse) {
        this.bakingServerRetrofitApi = new BakingServerRetrofitController().createRetrofitClient();
        this.stepListMapper = new ListMapper<>(new StepMapper());
        this.ingredientListMapper = new ListMapper<>(new IngredientMapper());
        this.recipeListMapper = new ListMapper<>(new RecipeMapper(ingredientListMapper, stepListMapper));
        this.cacheServerResponse = cacheServerResponse;
    }

    private com.josemgu91.bakingapp.data.remote.model.Recipe findRecipeById(List<com.josemgu91.bakingapp.data.remote.model.Recipe> recipes, String recipeId) {
        for (com.josemgu91.bakingapp.data.remote.model.Recipe recipe : recipes) {
            if (String.valueOf(recipe.id).equals(recipeId)) {
                return recipe;
            }
        }
        return null;
    }

    @Override
    public List<Step> getSteps(String recipeId) throws DataGatewayException {
        final List<com.josemgu91.bakingapp.data.remote.model.Recipe> recipes = getRecipesFromServerOrCache();
        final com.josemgu91.bakingapp.data.remote.model.Recipe recipe = findRecipeById(recipes, recipeId);
        if (recipe != null) {
            return stepListMapper.map(recipe.steps);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Recipe> getRecipes() throws DataGatewayException {
        final List<com.josemgu91.bakingapp.data.remote.model.Recipe> recipes = getRecipesFromServerOrCache();
        return recipeListMapper.map(recipes);
    }

    @Override
    public List<Ingredient> getIngredients(String recipeId) throws DataGatewayException {
        final List<com.josemgu91.bakingapp.data.remote.model.Recipe> recipes = getRecipesFromServerOrCache();
        final com.josemgu91.bakingapp.data.remote.model.Recipe recipe = findRecipeById(recipes, recipeId);
        if (recipe != null) {
            return ingredientListMapper.map(recipe.ingredients);
        } else {
            return new ArrayList<>();
        }
    }

    private boolean hasCachedResponse() {
        return cachedResponse != null;
    }

    private List<com.josemgu91.bakingapp.data.remote.model.Recipe> getRecipesFromServer() throws IOException {
        final Response<List<com.josemgu91.bakingapp.data.remote.model.Recipe>> serverResponse = bakingServerRetrofitApi.getRecipes().execute();
        if (serverResponse.isSuccessful()) {
            return serverResponse.body();
        } else {
            return null; //Maybe a remote server exception?
        }
    }

    private List<com.josemgu91.bakingapp.data.remote.model.Recipe> getRecipesFromServerOrCache() throws DataGatewayException {
        if (cacheServerResponse && hasCachedResponse()) {
            return cachedResponse;
        } else {
            try {
                final List<com.josemgu91.bakingapp.data.remote.model.Recipe> serverResponse = getRecipesFromServer();
                if (serverResponse != null) {
                    if (cacheServerResponse) {
                        cachedResponse = serverResponse;
                    }
                    return serverResponse;
                } else {
                    throw new DataGatewayException("The server has responded with an error code!");
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new DataGatewayException(e.getMessage());
            }
        }
    }

    private static class StepMapper implements OutputMapper<com.josemgu91.bakingapp.data.remote.model.Step, Step> {

        @Override
        public Step map(com.josemgu91.bakingapp.data.remote.model.Step step) {
            return new Step(
                    step.shortDescription,
                    step.description,
                    step.thumbnailURL,
                    step.videoURL
            );
        }
    }

    private static class IngredientMapper implements OutputMapper<com.josemgu91.bakingapp.data.remote.model.Ingredient, Ingredient> {

        @Override
        public Ingredient map(com.josemgu91.bakingapp.data.remote.model.Ingredient ingredient) {
            return new Ingredient(
                    ingredient.measure,
                    ingredient.ingredient,
                    ingredient.quantity
            );
        }
    }

    private static class RecipeMapper implements OutputMapper<com.josemgu91.bakingapp.data.remote.model.Recipe, Recipe> {

        private final ListMapper<com.josemgu91.bakingapp.data.remote.model.Ingredient, Ingredient> ingredientListMapper;
        private final ListMapper<com.josemgu91.bakingapp.data.remote.model.Step, Step> stepListMapper;

        public RecipeMapper(ListMapper<com.josemgu91.bakingapp.data.remote.model.Ingredient, Ingredient> ingredientListMapper, ListMapper<com.josemgu91.bakingapp.data.remote.model.Step, Step> stepListMapper) {
            this.ingredientListMapper = ingredientListMapper;
            this.stepListMapper = stepListMapper;
        }

        @Override
        public Recipe map(com.josemgu91.bakingapp.data.remote.model.Recipe recipe) {
            return new Recipe(
                    String.valueOf(recipe.id),
                    recipe.name,
                    recipe.servings,
                    recipe.image,
                    ingredientListMapper.map(recipe.ingredients),
                    stepListMapper.map(recipe.steps)
            );
        }
    }

}
