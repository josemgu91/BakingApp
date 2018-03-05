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

package com.josemgu91.bakingapp.android.ui.recipes_list;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.josemgu91.bakingapp.R;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipesViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 2/15/18.
 */

public class RecipesRecyclerViewAdapter extends RecyclerView.Adapter<RecipesRecyclerViewAdapter.RecipeViewHolder> {

    private final LayoutInflater layoutInflater;
    private final Context context;

    private List<GetRecipesViewModel.Recipe> recipeList;

    private OnRecipeSelectedListener onRecipeSelectedListener;

    private final Drawable defaultDrawable;

    public void setOnRecipeSelectedListener(OnRecipeSelectedListener onRecipeSelectedListener) {
        this.onRecipeSelectedListener = onRecipeSelectedListener;
    }

    public RecipesRecyclerViewAdapter(final Context context, final LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
        this.context = context;
        recipeList = new ArrayList<>();
        defaultDrawable = ContextCompat.getDrawable(context, R.drawable.ic_meal);
        DrawableCompat.setTint(defaultDrawable, ContextCompat.getColor(context, R.color.secondaryColor));
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeViewHolder(parent, context, layoutInflater);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        final GetRecipesViewModel.Recipe recipe = recipeList.get(position);
        holder.bind(recipe, onRecipeSelectedListener, defaultDrawable);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void setRecipes(final List<GetRecipesViewModel.Recipe> recipes) {
        recipeList = recipes;
        notifyDataSetChanged();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {

        private final Context context;

        private ImageView imageViewRecipePicture;
        private TextView textViewRecipeName;
        private TextView textViewRecipeServings;

        public RecipeViewHolder(final ViewGroup parent, final Context context, final LayoutInflater layoutInflater) {
            super(layoutInflater.inflate(R.layout.element_recipe, parent, false));
            this.context = context;
            imageViewRecipePicture = itemView.findViewById(R.id.imageview_recipe_picture);
            textViewRecipeName = itemView.findViewById(R.id.textview_recipe_name);
            textViewRecipeServings = itemView.findViewById(R.id.textview_recipe_servings);
        }

        public void bind(final GetRecipesViewModel.Recipe recipe,
                         final OnRecipeSelectedListener onRecipeSelectedListener,
                         final Drawable defaultDrawable) {
            textViewRecipeName.setText(recipe.getName());
            textViewRecipeServings.setText(context.getString(R.string.widget_recipe_servings, recipe.getServings()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecipeSelectedListener != null) {
                        onRecipeSelectedListener.onRecipeSelected(recipe);
                    }
                }
            });
            final String recipeUrl = recipe.getPictureUrl();
            if (!recipeUrl.isEmpty()) {
                Picasso.with(context)
                        .load(recipeUrl)
                        .error(defaultDrawable)
                        .into(imageViewRecipePicture);
            } else {
                imageViewRecipePicture.setImageDrawable(defaultDrawable);
            }
        }

    }

    public interface OnRecipeSelectedListener {

        void onRecipeSelected(GetRecipesViewModel.Recipe recipe);

    }

}
