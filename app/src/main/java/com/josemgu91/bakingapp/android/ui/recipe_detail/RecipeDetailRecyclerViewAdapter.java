package com.josemgu91.bakingapp.android.ui.recipe_detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.josemgu91.bakingapp.R;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeIngredientsViewModel;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeStepsViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 2/16/18.
 */

public class RecipeDetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_INGREDIENT = 0;
    private static final int VIEW_TYPE_STEP = 1;

    private final LayoutInflater layoutInflater;

    private List<GetRecipeIngredientsViewModel.Ingredient> ingredients;
    private List<GetRecipeStepsViewModel.Step> steps;

    private OnStepSelectedListener onStepSelectedListener;

    public void setOnStepSelectedListener(OnStepSelectedListener onStepSelectedListener) {
        this.onStepSelectedListener = onStepSelectedListener;
    }

    public RecipeDetailRecyclerViewAdapter(LayoutInflater layoutInflater) {
        this.ingredients = new ArrayList<>();
        this.steps = new ArrayList<>();
        this.layoutInflater = layoutInflater;
    }

    public void setIngredients(List<GetRecipeIngredientsViewModel.Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    public void setSteps(List<GetRecipeStepsViewModel.Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_INGREDIENT:
                return new IngredientViewHolder(parent, layoutInflater);
            case VIEW_TYPE_STEP:
                return new StepViewHolder(parent, layoutInflater);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_INGREDIENT:
                ((IngredientViewHolder) holder).bind(ingredients.get(position));
                break;
            case VIEW_TYPE_STEP:
                ((StepViewHolder) holder).bind(steps.get(position - ingredients.size()), onStepSelectedListener);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position - ingredients.size() < 0) {
            return VIEW_TYPE_INGREDIENT;
        } else {
            return VIEW_TYPE_STEP;
        }
    }

    @Override
    public int getItemCount() {
        return ingredients.size() + steps.size();
    }

    private static class IngredientViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewIngredientName;

        public IngredientViewHolder(final ViewGroup parent, final LayoutInflater layoutInflater) {
            super(layoutInflater.inflate(R.layout.element_recipe_ingredient, parent, false));
            textViewIngredientName = itemView.findViewById(R.id.textview_recipe_ingredient_name);
        }

        public void bind(final GetRecipeIngredientsViewModel.Ingredient ingredient) {
            textViewIngredientName.setText(ingredient.getName());
        }
    }

    private static class StepViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewStepName;

        public StepViewHolder(final ViewGroup parent, final LayoutInflater layoutInflater) {
            super(layoutInflater.inflate(R.layout.element_recipe_step, parent, false));
            textViewStepName = itemView.findViewById(R.id.textview_recipe_step_name);
        }

        public void bind(final GetRecipeStepsViewModel.Step step, final OnStepSelectedListener onStepSelectedListener) {
            textViewStepName.setText(step.getShortDescription());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onStepSelectedListener != null) {
                        onStepSelectedListener.onStepSelected(step);
                    }
                }
            });
        }
    }

    public interface OnStepSelectedListener {

        void onStepSelected(final GetRecipeStepsViewModel.Step step);

    }
}