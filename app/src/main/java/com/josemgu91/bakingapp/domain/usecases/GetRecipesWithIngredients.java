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

package com.josemgu91.bakingapp.domain.usecases;

import com.josemgu91.bakingapp.domain.datagateways.DataGatewayException;
import com.josemgu91.bakingapp.domain.datagateways.RecipeDataGateway;
import com.josemgu91.bakingapp.domain.entities.Ingredient;
import com.josemgu91.bakingapp.domain.entities.Recipe;
import com.josemgu91.bakingapp.domain.usecases.common.AbstractGetUseCase;
import com.josemgu91.bakingapp.domain.usecases.common.GetUseCaseOutput;
import com.josemgu91.bakingapp.domain.util.ListMapper;
import com.josemgu91.bakingapp.domain.util.OutputMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 2/22/18.
 */

public class GetRecipesWithIngredients extends AbstractGetUseCase<List<Recipe>, List<RecipeWithIngredientsOutput>> {

    private final RecipeDataGateway recipeDataGateway;

    public GetRecipesWithIngredients(GetUseCaseOutput<List<RecipeWithIngredientsOutput>> getUseCaseOutput, RecipeDataGateway recipeDataGateway) {
        super(getUseCaseOutput, new ListMapper<>(new RecipeWithIngredientsMapper()));
        this.recipeDataGateway = recipeDataGateway;
    }

    @Override
    protected List<Recipe> getData() throws DataGatewayException {
        return recipeDataGateway.getRecipes();
    }

    private static class RecipeWithIngredientsMapper implements OutputMapper<Recipe, RecipeWithIngredientsOutput> {

        @Override
        public RecipeWithIngredientsOutput map(Recipe recipe) {
            final List<RecipeWithIngredientsOutput.Ingredient> ingredients = new ArrayList<>();
            for (final Ingredient ingredient : recipe.getIngredients()) {
                ingredients.add(new RecipeWithIngredientsOutput.Ingredient(
                        ingredient.getMeasureUnit(),
                        ingredient.getName(),
                        ingredient.getQuantity()
                ));
            }
            return new RecipeWithIngredientsOutput(
                    recipe.getId(),
                    recipe.getName(),
                    recipe.getServings(),
                    recipe.getPictureUrl(),
                    ingredients
            );
        }
    }

}
