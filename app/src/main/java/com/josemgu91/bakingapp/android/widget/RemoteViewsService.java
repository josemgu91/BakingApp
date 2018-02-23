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
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.RemoteViews;

import com.josemgu91.bakingapp.R;

import java.util.List;

/**
 * Created by jose on 2/23/18.
 */

public class RemoteViewsService extends android.widget.RemoteViewsService {

    public final static String PARAM_RECIPES = "com.josemgu91.bakingapp.RECIPES";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        if (intent.hasExtra(PARAM_RECIPES)) {
            final List<Recipe> recipes = intent.getParcelableArrayListExtra(PARAM_RECIPES);
            return new RemoteViewsFactory(this, recipes);
        } else {
            throw new IllegalStateException("The Intent hasn't the PARAM_RECIPES content!");
        }
    }

    public static class RemoteViewsFactory implements android.widget.RemoteViewsService.RemoteViewsFactory {

        private final Context context;
        private final List<Recipe> recipes;

        public RemoteViewsFactory(Context context, List<Recipe> recipes) {
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
            final Recipe recipe = recipes.get(position);
            final RemoteViews remoteViewsRecipe = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_element);
            remoteViewsRecipe.setTextViewText(R.id.textview_recipe_name, recipe.name);
            for (final Ingredient ingredient : recipe.ingredients) {
                final RemoteViews remoteViewsIngredient = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_ingredient);
                remoteViewsIngredient.setTextViewText(R.id.textview_recipe_ingredient_name, ingredient.name);
                remoteViewsRecipe.addView(R.id.linearlayout_recipe_element, remoteViewsIngredient);
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
            return Long.valueOf(recipes.get(position).id);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    public static class Recipe implements Parcelable {

        private final String id;
        private final String name;
        private final int servings;
        private final String pictureUrl;
        private final List<Ingredient> ingredients;

        public Recipe(String id, String name, int servings, String pictureUrl, List<Ingredient> ingredients) {
            this.id = id;
            this.name = name;
            this.servings = servings;
            this.pictureUrl = pictureUrl;
            this.ingredients = ingredients;
        }

        protected Recipe(Parcel in) {
            id = in.readString();
            name = in.readString();
            servings = in.readInt();
            pictureUrl = in.readString();
            ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        }

        public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
            @Override
            public Recipe createFromParcel(Parcel in) {
                return new Recipe(in);
            }

            @Override
            public Recipe[] newArray(int size) {
                return new Recipe[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
            dest.writeInt(servings);
            dest.writeString(pictureUrl);
            dest.writeTypedList(ingredients);
        }
    }

    public static class Ingredient implements Parcelable {

        private final String measureUnit;
        private final String name;
        private final double quantity;

        public Ingredient(String measureUnit, String name, double quantity) {
            this.measureUnit = measureUnit;
            this.name = name;
            this.quantity = quantity;
        }

        protected Ingredient(Parcel in) {
            measureUnit = in.readString();
            name = in.readString();
            quantity = in.readDouble();
        }

        public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
            @Override
            public Ingredient createFromParcel(Parcel in) {
                return new Ingredient(in);
            }

            @Override
            public Ingredient[] newArray(int size) {
                return new Ingredient[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(measureUnit);
            dest.writeString(name);
            dest.writeDouble(quantity);
        }
    }

}
