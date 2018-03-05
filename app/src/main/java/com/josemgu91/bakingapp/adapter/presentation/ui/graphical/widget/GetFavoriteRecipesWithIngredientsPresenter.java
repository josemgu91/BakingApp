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

package com.josemgu91.bakingapp.adapter.presentation.ui.graphical.widget;

import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetView;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.common.AbstractGetPresenter;
import com.josemgu91.bakingapp.domain.usecases.RecipeWithIngredientsOutput;
import com.josemgu91.bakingapp.domain.util.ListMapper;
import com.josemgu91.bakingapp.domain.util.OutputMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by jose on 2/22/18.
 */

public class GetFavoriteRecipesWithIngredientsPresenter extends AbstractGetPresenter<List<RecipeWithIngredientsOutput>, GetFavoriteRecipesWithIngredientsViewModel> {

    private ListMapper<RecipeWithIngredientsOutput, GetFavoriteRecipesWithIngredientsViewModel.Recipe> recipesOutputListMapper;

    public GetFavoriteRecipesWithIngredientsPresenter(GetView<GetFavoriteRecipesWithIngredientsViewModel> view, Executor viewExecutor) {
        super(view, viewExecutor);
        this.recipesOutputListMapper = new ListMapper<>(new RecipesWithIngredientsOutputMapper());
    }

    @Override
    protected GetFavoriteRecipesWithIngredientsViewModel createResultViewModel(List<RecipeWithIngredientsOutput> recipeWithIngredientsOutputs) {
        return new GetFavoriteRecipesWithIngredientsViewModel(
                recipesOutputListMapper.map(recipeWithIngredientsOutputs)
        );
    }

    private class RecipesWithIngredientsOutputMapper implements OutputMapper<RecipeWithIngredientsOutput, GetFavoriteRecipesWithIngredientsViewModel.Recipe> {

        @Override
        public GetFavoriteRecipesWithIngredientsViewModel.Recipe map(RecipeWithIngredientsOutput recipeWithIngredientsOutput) {
            final List<GetFavoriteRecipesWithIngredientsViewModel.Recipe.Ingredient> ingredients = new ArrayList<>();
            for (final RecipeWithIngredientsOutput.Ingredient ingredient : recipeWithIngredientsOutput.getIngredients()) {
                ingredients.add(
                        new GetFavoriteRecipesWithIngredientsViewModel.Recipe.Ingredient(
                                ingredient.getMeasureUnit(),
                                ingredient.getName(),
                                ingredient.getQuantity()
                        )
                );
            }
            return new GetFavoriteRecipesWithIngredientsViewModel.Recipe(
                    recipeWithIngredientsOutput.getId(),
                    recipeWithIngredientsOutput.getName(),
                    recipeWithIngredientsOutput.getServings(),
                    recipeWithIngredientsOutput.getPictureUrl(),
                    ingredients
            );
        }
    }
}
