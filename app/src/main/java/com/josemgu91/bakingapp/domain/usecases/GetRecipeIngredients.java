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
import com.josemgu91.bakingapp.domain.datagateways.IngredientDataGateway;
import com.josemgu91.bakingapp.domain.entities.Ingredient;
import com.josemgu91.bakingapp.domain.usecases.common.AbstractGetUseCase;
import com.josemgu91.bakingapp.domain.util.ListMapper;
import com.josemgu91.bakingapp.domain.util.OutputMapper;
import com.josemgu91.bakingapp.domain.usecases.common.GetUseCaseOutput;

import java.util.List;

/**
 * Created by jose on 2/14/18.
 */

public class GetRecipeIngredients extends AbstractGetUseCase<List<Ingredient>, List<IngredientOutput>> {

    private final IngredientDataGateway ingredientDataGateway;
    private final String recipeId;

    public GetRecipeIngredients(GetUseCaseOutput<List<IngredientOutput>> getUseCaseOutput, IngredientDataGateway ingredientDataGateway, String recipeId) {
        super(getUseCaseOutput, new ListMapper<>(new IngredientMapper()));
        this.ingredientDataGateway = ingredientDataGateway;
        this.recipeId = recipeId;
    }

    @Override
    protected List<Ingredient> getData() throws DataGatewayException {
        return ingredientDataGateway.getIngredients(recipeId);
    }

    private static class IngredientMapper implements OutputMapper<Ingredient, IngredientOutput> {

        @Override
        public IngredientOutput map(Ingredient ingredient) {
            return new IngredientOutput(
                    ingredient.getMeasureUnit(),
                    ingredient.getName(),
                    ingredient.getQuantity()
            );
        }
    }
}
