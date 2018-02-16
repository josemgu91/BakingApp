package com.josemgu91.bakingapp.android.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
        final String recipeName = recipeList.get(position).getName();
        holder.textViewRecipeName.setText(recipeName);
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

    }

}
