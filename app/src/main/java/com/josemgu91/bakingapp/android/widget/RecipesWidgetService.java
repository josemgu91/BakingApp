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

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.josemgu91.bakingapp.R;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.View;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.widget.GetRecipesWithIngredientsController;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.widget.GetRecipesWithIngredientsViewModel;
import com.josemgu91.bakingapp.android.ui.ControllerFactoryImpl;
import com.josemgu91.bakingapp.domain.util.ListMapper;
import com.josemgu91.bakingapp.domain.util.OutputMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 2/21/18.
 */

public class RecipesWidgetService extends Service implements View<GetRecipesWithIngredientsViewModel> {

    @Override
    public void onCreate() {
        super.onCreate();
        final GetRecipesWithIngredientsController getRecipesWithIngredientsController = new ControllerFactoryImpl(this).createGetRecipesWithIngredientsController(this);
        getRecipesWithIngredientsController.getRecipesWithIngredients();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void showResult(GetRecipesWithIngredientsViewModel getRecipesWithIngredientsViewModel) {
        final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_recipes);
        final Intent remoteViewsServiceIntent = new Intent(this, RemoteViewsService.class)
                .putParcelableArrayListExtra(RemoteViewsService.EXTRA_RECIPES, mapRecipesToParcelableRecipes(getRecipesWithIngredientsViewModel.getRecipes()));
        remoteViews.setRemoteAdapter(R.id.listview_recipes, remoteViewsServiceIntent);
    }

    @Override
    public void showInProgress() {

    }

    @Override
    public void showRetrieveError() {

    }

    @Override
    public void showNoResult() {

    }

    private ArrayList<RemoteViewsService.Recipe> mapRecipesToParcelableRecipes(List<GetRecipesWithIngredientsViewModel.Recipe> recipes) {
        final ListMapper<GetRecipesWithIngredientsViewModel.Recipe, RemoteViewsService.Recipe> listMapper = new ListMapper<>(new RecipeViewModelToParcelableRecipeMapper());
        return new ArrayList<>(listMapper.map(recipes));
    }

    private static class RecipeViewModelToParcelableRecipeMapper implements OutputMapper<GetRecipesWithIngredientsViewModel.Recipe, RemoteViewsService.Recipe> {

        @Override
        public RemoteViewsService.Recipe map(GetRecipesWithIngredientsViewModel.Recipe recipe) {
            final List<RemoteViewsService.Recipe.Ingredient> ingredients = new ArrayList<>();
            for (final GetRecipesWithIngredientsViewModel.Recipe.Ingredient ingredient : recipe.getIngredients()) {
                ingredients.add(new RemoteViewsService.Recipe.Ingredient(
                        ingredient.getMeasureUnit(),
                        ingredient.getName(),
                        ingredient.getQuantity()
                ));
            }
            return new RemoteViewsService.Recipe(
                    recipe.getId(),
                    recipe.getName(),
                    recipe.getServings(),
                    recipe.getPictureUrl(),
                    ingredients
            );
        }
    }

}
