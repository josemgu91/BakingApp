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

package com.josemgu91.bakingapp.android.ui.recipe_detail;

import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeIngredientsController;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeIngredientsViewModel;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeStepsController;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeStepsViewModel;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.View;
import com.josemgu91.bakingapp.android.ui.ControllerFactory;

/**
 * Created by jose on 2/16/18.
 */

public class RecipeDetailViewInterfaceAdapter {

    private final GetRecipeStepsController getRecipeStepsController;
    private final GetRecipeIngredientsController getRecipeIngredientsController;

    public RecipeDetailViewInterfaceAdapter(final ControllerFactory controllerFactory, final RecipeDetailViewInterface recipeDetailViewInterface) {
        this.getRecipeStepsController = controllerFactory.createGetRecipeStepsController(new View<GetRecipeStepsViewModel>() {
            @Override
            public void showResult(GetRecipeStepsViewModel getRecipeStepsViewModel) {
                recipeDetailViewInterface.showSteps(getRecipeStepsViewModel);
            }

            @Override
            public void showInProgress() {
                recipeDetailViewInterface.showStepsInProgress();
            }

            @Override
            public void showRetrieveError() {
                recipeDetailViewInterface.showStepsRetrieveError();
            }

            @Override
            public void showNoResult() {
                recipeDetailViewInterface.showStepsNoResult();
            }
        });
        this.getRecipeIngredientsController = controllerFactory.createGetRecipeIngredientsController(new View<GetRecipeIngredientsViewModel>() {
            @Override
            public void showResult(GetRecipeIngredientsViewModel getRecipeIngredientsViewModel) {
                recipeDetailViewInterface.showIngredients(getRecipeIngredientsViewModel);
            }

            @Override
            public void showInProgress() {
                recipeDetailViewInterface.showIngredientsInProgress();
            }

            @Override
            public void showRetrieveError() {
                recipeDetailViewInterface.showIngredientsRetrieveError();
            }

            @Override
            public void showNoResult() {
                recipeDetailViewInterface.showIngredientsNoResult();
            }
        });
    }

    public GetRecipeStepsController getGetRecipeStepsController() {
        return getRecipeStepsController;
    }

    public GetRecipeIngredientsController getGetRecipeIngredientsController() {
        return getRecipeIngredientsController;
    }
}
