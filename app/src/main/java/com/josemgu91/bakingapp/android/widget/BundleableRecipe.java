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

package com.josemgu91.bakingapp.android.widget;

import android.os.Bundle;

import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.widget.GetRecipesWithIngredientsViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 2/23/18.
 */

public class BundleableRecipe {

    private final static String RECIPE_LIST_SIZE = "recipeListSize";

    private final static String RECIPE_ID = "recipe_id";
    private final static String RECIPE_NAME = "recipe_name";
    private final static String RECIPE_SERVINGS = "recipe_servings";
    private final static String RECIPE_PICTURE_URL = "recipe_picture_url";
    private final static String RECIPE_INGREDIENTS = "recipe_ingredients";

    private final static String INGREDIENT_LIST_SIZE = "ingredientListSize";

    private final static String INGREDIENT_MEASURE_UNIT = "ingredient_measure_unit";
    private final static String INGREDIENT_NAME = "ingredient_name";
    private final static String INGREDIENT_QUANTITY = "ingredient_quantity";

    public Bundle toBundle(List<GetRecipesWithIngredientsViewModel.Recipe> recipes) {
        final Bundle recipeListBundle = new Bundle();
        final int recipeListSize = recipes.size();
        recipeListBundle.putInt(RECIPE_LIST_SIZE, recipeListSize);
        for (int recipeIndex = 0; recipeIndex < recipeListSize; recipeIndex++) {
            final GetRecipesWithIngredientsViewModel.Recipe recipe = recipes.get(recipeIndex);
            final Bundle recipeBundle = new Bundle();
            recipeBundle.putString(RECIPE_ID, recipe.getId());
            recipeBundle.putString(RECIPE_NAME, recipe.getName());
            recipeBundle.putInt(RECIPE_SERVINGS, recipe.getServings());
            recipeBundle.putString(RECIPE_PICTURE_URL, recipe.getPictureUrl());
            final int ingredientListSize = recipe.getIngredients().size();
            recipeBundle.putInt(INGREDIENT_LIST_SIZE, ingredientListSize);
            final Bundle ingredientListBundle = new Bundle();
            for (int ingredientIndex = 0; ingredientIndex < ingredientListSize; ingredientIndex++) {
                final GetRecipesWithIngredientsViewModel.Recipe.Ingredient ingredient = recipe.getIngredients().get(ingredientIndex);
                final Bundle ingredientBundle = new Bundle();
                ingredientBundle.putString(INGREDIENT_MEASURE_UNIT, ingredient.getMeasureUnit());
                ingredientBundle.putString(INGREDIENT_NAME, ingredient.getName());
                ingredientBundle.putDouble(INGREDIENT_QUANTITY, ingredient.getQuantity());
                ingredientListBundle.putBundle("ingredient" + ingredientIndex, ingredientBundle);
            }
            recipeBundle.putBundle(RECIPE_INGREDIENTS, ingredientListBundle);
            recipeListBundle.putBundle("recipe" + recipeIndex, recipeBundle);
        }
        return recipeListBundle;
    }

    public List<GetRecipesWithIngredientsViewModel.Recipe> fromBundle(final Bundle bundle) {
        final List<GetRecipesWithIngredientsViewModel.Recipe> recipeList = new ArrayList<>();
        final int recipeListSize = bundle.getInt(RECIPE_LIST_SIZE);
        for (int recipeIndex = 0; recipeIndex < recipeListSize; recipeIndex++) {
            final Bundle recipeBundle = bundle.getBundle("recipe" + recipeIndex);
            final List<GetRecipesWithIngredientsViewModel.Recipe.Ingredient> ingredientList = new ArrayList<>();
            final int ingredientListSize = recipeBundle.getInt(INGREDIENT_LIST_SIZE);
            if (recipeListSize > 0) {
                final Bundle ingredientListBundle = recipeBundle.getBundle(RECIPE_INGREDIENTS);
                for (int ingredientIndex = 0; ingredientIndex < ingredientListSize; ingredientIndex++) {
                    final Bundle ingredientBundle = ingredientListBundle.getBundle("ingredient" + ingredientIndex);
                    final GetRecipesWithIngredientsViewModel.Recipe.Ingredient ingredient = new GetRecipesWithIngredientsViewModel.Recipe.Ingredient(
                            ingredientBundle.getString(INGREDIENT_MEASURE_UNIT),
                            ingredientBundle.getString(INGREDIENT_NAME),
                            ingredientBundle.getDouble(INGREDIENT_QUANTITY)
                    );
                    ingredientList.add(ingredient);
                }
            }
            final GetRecipesWithIngredientsViewModel.Recipe recipe = new GetRecipesWithIngredientsViewModel.Recipe(
                    recipeBundle.getString(RECIPE_ID),
                    recipeBundle.getString(RECIPE_NAME),
                    recipeBundle.getInt(RECIPE_SERVINGS),
                    recipeBundle.getString(RECIPE_PICTURE_URL),
                    ingredientList
            );
            recipeList.add(recipe);
        }
        return recipeList;
    }

}