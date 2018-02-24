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

package com.josemgu91.bakingapp;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;

import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.widget.GetRecipesWithIngredientsViewModel;
import com.josemgu91.bakingapp.android.widget.RecipeBundleMapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 2/23/18.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeBundleMapperTest {

    private List<GetRecipesWithIngredientsViewModel.Recipe> createTestRecipes(int size) {
        final List<GetRecipesWithIngredientsViewModel.Recipe> testRecipeList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            final List<GetRecipesWithIngredientsViewModel.Recipe.Ingredient> ingredientList = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                final GetRecipesWithIngredientsViewModel.Recipe.Ingredient testIngredient = new GetRecipesWithIngredientsViewModel.Recipe.Ingredient(
                        "testMeasure" + j,
                        "testName" + j,
                        j
                );
                ingredientList.add(testIngredient);
            }
            final GetRecipesWithIngredientsViewModel.Recipe testRecipe = new GetRecipesWithIngredientsViewModel.Recipe(
                    String.valueOf(i),
                    "testName" + i,
                    i,
                    String.valueOf(i),
                    ingredientList
            );
            testRecipeList.add(testRecipe);
        }
        return testRecipeList;
    }

    @Test
    public void testRecipeBundleMapper() {
        final int testListSize = 100;
        final List<GetRecipesWithIngredientsViewModel.Recipe> originalRecipeList = createTestRecipes(testListSize);
        final RecipeBundleMapper recipeBundleMapper = new RecipeBundleMapper();
        final Bundle bundledRecipeList = recipeBundleMapper.toBundle(originalRecipeList);
        final List<GetRecipesWithIngredientsViewModel.Recipe> restoredRecipeList = recipeBundleMapper.fromBundle(bundledRecipeList);
        for (int i = 0; i < testListSize; i++) {
            Assert.assertTrue(originalRecipeList.get(i).equals(restoredRecipeList.get(i)));
        }
    }

}
