package com.josemgu91.bakingapp.android.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.josemgu91.bakingapp.R;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipesViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 2/15/18.
 */

public class RecipesRecyclerViewAdapter extends RecyclerView.Adapter<RecipesRecyclerViewAdapter.RecipeViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<GetRecipesViewModel.Recipe> recipeList;

    private OnRecipeSelectedListener onRecipeSelectedListener;

    public void setOnRecipeSelectedListener(OnRecipeSelectedListener onRecipeSelectedListener) {
        this.onRecipeSelectedListener = onRecipeSelectedListener;
    }

    public RecipesRecyclerViewAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
        recipeList = new ArrayList<>();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeViewHolder(parent, layoutInflater);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        final GetRecipesViewModel.Recipe recipe = recipeList.get(position);
        holder.bind(recipe, onRecipeSelectedListener);
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

        private TextView textViewRecipeName;

        public RecipeViewHolder(final ViewGroup parent, final LayoutInflater layoutInflater) {
            super(layoutInflater.inflate(R.layout.element_recipe, parent, false));
            textViewRecipeName = itemView.findViewById(R.id.textview_recipe_name);
        }

        public void bind(final GetRecipesViewModel.Recipe recipe, final OnRecipeSelectedListener onRecipeSelectedListener) {
            textViewRecipeName.setText(recipe.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecipeSelectedListener != null) {
                        onRecipeSelectedListener.onRecipeSelected(recipe);
                    }
                }
            });
        }

    }

    public interface OnRecipeSelectedListener {

        void onRecipeSelected(GetRecipesViewModel.Recipe recipe);

    }

}
