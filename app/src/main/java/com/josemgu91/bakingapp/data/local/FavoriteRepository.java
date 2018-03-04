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

package com.josemgu91.bakingapp.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.josemgu91.bakingapp.domain.datagateways.DataGatewayException;
import com.josemgu91.bakingapp.domain.datagateways.FavoriteRecipesDataGateway;
import com.josemgu91.bakingapp.domain.datagateways.RecipeDataGateway;
import com.josemgu91.bakingapp.domain.entities.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 3/3/18.
 */

public class FavoriteRepository implements FavoriteRecipesDataGateway {

    private final RecipeDataGateway recipeDataGateway;
    private final SqLiteFavoriteRepository sqLiteFavoriteRepository;

    public FavoriteRepository(final SqLiteFavoriteRepository sqLiteFavoriteRepository, final RecipeDataGateway recipeDataGateway) {
        this.recipeDataGateway = recipeDataGateway;
        this.sqLiteFavoriteRepository = sqLiteFavoriteRepository;
    }

    @Override
    public boolean saveFavoriteRecipe(String recipeId) throws DataGatewayException {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(SqLiteFavoriteRepository.TABLE_FAVORITE_RECIPES_COLUMN_RECIPE_ID, recipeId);
        return sqLiteFavoriteRepository
                .getWritableDatabase()
                .insert(SqLiteFavoriteRepository.TABLE_FAVORITE_RECIPES, null, contentValues)
                != -1;
    }

    @Override
    public boolean isFavorite(String recipeId) throws DataGatewayException {
        final Cursor cursor = sqLiteFavoriteRepository
                .getReadableDatabase()
                .query(
                        SqLiteFavoriteRepository.TABLE_FAVORITE_RECIPES,
                        null,
                        SqLiteFavoriteRepository.TABLE_FAVORITE_RECIPES_COLUMN_RECIPE_ID + "=?",
                        new String[]{recipeId},
                        null,
                        null,
                        null
                );
        final boolean isFavorite = cursor.getCount() > 0;
        cursor.close();
        return isFavorite;
    }

    @Override
    public boolean deleteFavoriteRecipe(String recipeId) throws DataGatewayException {
        return sqLiteFavoriteRepository
                .getWritableDatabase()
                .delete(
                        SqLiteFavoriteRepository.TABLE_FAVORITE_RECIPES,
                        SqLiteFavoriteRepository.TABLE_FAVORITE_RECIPES_COLUMN_RECIPE_ID + "=?",
                        new String[]{recipeId}
                ) > 0;
    }

    @Override
    public List<Recipe> getFavoriteRecipes() throws DataGatewayException {
        final List<Recipe> recipes = recipeDataGateway.getRecipes();
        final ArrayList<Recipe> favoriteRecipes = new ArrayList<>();
        for (final Recipe recipe : recipes) {
            if (isFavorite(recipe.getId())) {
                favoriteRecipes.add(recipe);
            }
        }
        return favoriteRecipes;
    }
}
