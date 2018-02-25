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

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.josemgu91.bakingapp.R;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.widget.GetRecipesWithIngredientsViewModel;
import com.josemgu91.bakingapp.android.ui.MainActivity;

import java.util.List;

/**
 * Created by jose on 2/23/18.
 */

public class RemoteViewsService extends android.widget.RemoteViewsService {

    public final static String PARAM_RECIPES = "com.josemgu91.bakingapp.RECIPES";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        if (intent.hasExtra(PARAM_RECIPES)) {
            final List<GetRecipesWithIngredientsViewModel.Recipe> recipes = new RecipeBundleMapper().fromBundle(intent.getBundleExtra(PARAM_RECIPES));
            return new RemoteViewsFactory(this, recipes);
        } else {
            throw new IllegalStateException("The Intent hasn't the PARAM_RECIPES content!");
        }
    }

    public static class RemoteViewsFactory implements android.widget.RemoteViewsService.RemoteViewsFactory {

        private final Context context;
        private final List<GetRecipesWithIngredientsViewModel.Recipe> recipes;

        public RemoteViewsFactory(Context context, List<GetRecipesWithIngredientsViewModel.Recipe> recipes) {
            this.context = context;
            this.recipes = recipes;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return recipes.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            final GetRecipesWithIngredientsViewModel.Recipe recipe = recipes.get(position);
            final RemoteViews remoteViewsRecipe = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_element);
            remoteViewsRecipe.setOnClickFillInIntent(
                    R.id.linearlayout_recipe_element,
                    new Intent()
                            .putExtra(MainActivity.PARAM_RECIPE_ID, recipe.getId())
            );
            remoteViewsRecipe.setTextViewText(R.id.textview_recipe_name, recipe.getName());
            remoteViewsRecipe.setTextViewText(R.id.textview_recipe_servings, context.getString(R.string.widget_recipe_servings, recipe.getServings()));
            remoteViewsRecipe.removeAllViews(R.id.linearlayout_ingredients);
            for (final GetRecipesWithIngredientsViewModel.Recipe.Ingredient ingredient : recipe.getIngredients()) {
                final RemoteViews remoteViewsIngredient = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_ingredient);
                remoteViewsIngredient.setTextViewText(R.id.textview_recipe_ingredient_name, ingredient.getName());
                remoteViewsIngredient.setTextViewText(R.id.textview_recipe_ingredient_quantity, String.valueOf(ingredient.getQuantity()));
                remoteViewsIngredient.setTextViewText(R.id.textview_recipe_ingredient_measure_unit, ingredient.getMeasureUnit());
                remoteViewsRecipe.addView(R.id.linearlayout_ingredients, remoteViewsIngredient);
            }
            return remoteViewsRecipe;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return Long.valueOf(recipes.get(position).getId());
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}
