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

package com.josemgu91.bakingapp.adapter.presentation.ui.graphical;

import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.common.AbstractGetPresenter;
import com.josemgu91.bakingapp.domain.usecases.IngredientOutput;
import com.josemgu91.bakingapp.domain.util.ListMapper;
import com.josemgu91.bakingapp.domain.util.OutputMapper;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by jose on 2/16/18.
 */

public class GetRecipeIngredientsPresenter extends AbstractGetPresenter<List<IngredientOutput>, GetRecipeIngredientsViewModel> {

    private final ListMapper<IngredientOutput, GetRecipeIngredientsViewModel.Ingredient> ingredientOutputListMapper;

    public GetRecipeIngredientsPresenter(View<GetRecipeIngredientsViewModel> view, Executor viewExecutor) {
        super(view, viewExecutor);
        this.ingredientOutputListMapper = new ListMapper<>(new IngredientOutputMapper());
    }

    @Override
    protected GetRecipeIngredientsViewModel createResultViewModel(List<IngredientOutput> ingredientOutput) {
        return new GetRecipeIngredientsViewModel(
                ingredientOutputListMapper.map(ingredientOutput)
        );
    }

    private class IngredientOutputMapper implements OutputMapper<IngredientOutput, GetRecipeIngredientsViewModel.Ingredient> {

        @Override
        public GetRecipeIngredientsViewModel.Ingredient map(IngredientOutput ingredientOutput) {
            return new GetRecipeIngredientsViewModel.Ingredient(
                    ingredientOutput.getMeasureUnit(),
                    ingredientOutput.getName(),
                    ingredientOutput.getQuantity()
            );
        }
    }

}
