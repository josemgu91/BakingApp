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

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.josemgu91.bakingapp.R;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeIngredientsViewModel;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeStepsViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 2/16/18.
 */

public class RecipeDetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_INGREDIENT = 0;
    private static final int VIEW_TYPE_STEP = 1;
    private static final int VIEW_TYPE_SECTION_HEADER = 2;

    private final Context context;
    private final LayoutInflater layoutInflater;

    private List<GetRecipeIngredientsViewModel.Ingredient> ingredients;
    private List<GetRecipeStepsViewModel.Step> steps;

    private RelativeIndexCalculator relativeIndexCalculator;

    private OnStepSelectedListener onStepSelectedListener;

    private final Drawable defaultStepDrawable;

    public void setOnStepSelectedListener(OnStepSelectedListener onStepSelectedListener) {
        this.onStepSelectedListener = onStepSelectedListener;
    }

    public RecipeDetailRecyclerViewAdapter(final Context context, final LayoutInflater layoutInflater) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.ingredients = new ArrayList<>();
        this.steps = new ArrayList<>();
        updateRelativeIndexCalculator();
        defaultStepDrawable = ContextCompat.getDrawable(context, R.drawable.ic_step);
        DrawableCompat.setTint(DrawableCompat.wrap(defaultStepDrawable), ContextCompat.getColor(context, R.color.secondaryColor));
    }

    public void setIngredients(List<GetRecipeIngredientsViewModel.Ingredient> ingredients) {
        this.ingredients = ingredients;
        updateRelativeIndexCalculator();
        notifyDataSetChanged();
    }

    public void setSteps(List<GetRecipeStepsViewModel.Step> steps) {
        this.steps = steps;
        updateRelativeIndexCalculator();
        notifyDataSetChanged();
    }

    private void updateRelativeIndexCalculator() {
        relativeIndexCalculator = new RelativeIndexCalculator(
                true,
                new int[]{VIEW_TYPE_INGREDIENT, VIEW_TYPE_STEP},
                ingredients,
                steps
        );
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_INGREDIENT:
                return new IngredientViewHolder(parent, context, layoutInflater);
            case VIEW_TYPE_STEP:
                return new StepViewHolder(parent, context, layoutInflater);
            case VIEW_TYPE_SECTION_HEADER:
                return new SectionHeaderViewHolder(parent, layoutInflater);
            default:
                throw new IllegalStateException("Unknown viewType!");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int viewType = getItemViewType(position);
        final RelativeIndexCalculator.IndexInfo indexInfo = relativeIndexCalculator.getRelativeIndex(position);
        switch (viewType) {
            case VIEW_TYPE_INGREDIENT:
                ((IngredientViewHolder) holder).bind(ingredients.get(indexInfo.relativeIndex));
                break;
            case VIEW_TYPE_STEP:
                ((StepViewHolder) holder).bind(indexInfo.relativeIndex + 1, steps.get(indexInfo.relativeIndex), onStepSelectedListener, defaultStepDrawable);
                break;
            case VIEW_TYPE_SECTION_HEADER:
                @StringRes final int sectionName = indexInfo.listType == VIEW_TYPE_INGREDIENT ? R.string.recipe_detail_header_ingredients : R.string.recipe_detail_header_steps;
                ((SectionHeaderViewHolder) holder).bind(context.getString(sectionName));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        final RelativeIndexCalculator.IndexInfo indexInfo = relativeIndexCalculator.getRelativeIndex(position);
        if (indexInfo.isHeader) {
            return VIEW_TYPE_SECTION_HEADER;
        } else if (indexInfo.listType != RelativeIndexCalculator.NO_TYPE) {
            switch (indexInfo.listType) {
                case VIEW_TYPE_INGREDIENT:
                    return VIEW_TYPE_INGREDIENT;
                case VIEW_TYPE_STEP:
                    return VIEW_TYPE_STEP;
            }
        }
        throw new IllegalStateException("Invalid position: " + position);
    }

    @Override
    public int getItemCount() {
        return ingredients.size() + steps.size() + 2;
    }

    private static class RelativeIndexCalculator {

        private final int[] listTypes;
        private final List[] lists;
        private final boolean withHeaders;

        public final static int OUT_OF_RANGE = -1;
        public final static int NO_TYPE = -2;

        public RelativeIndexCalculator(final boolean withHeaders, final int[] listTypes, final List... lists) {
            this.listTypes = listTypes;
            this.lists = lists;
            this.withHeaders = withHeaders;
        }

        public IndexInfo getRelativeIndex(final int absoluteIndex) {
            if (absoluteIndex < 0) {
                return new IndexInfo(OUT_OF_RANGE, NO_TYPE, false);
            }
            int diff = absoluteIndex;
            for (int i = 0; i < lists.length; i++) {
                final int currentListSize;
                if (withHeaders) {
                    currentListSize = lists[i].size() + 1;
                } else {
                    currentListSize = lists[i].size();
                }
                if (withHeaders && diff == 0) {
                    return new IndexInfo(OUT_OF_RANGE, listTypes[i], true);
                }
                final int preDiff = diff - currentListSize;
                if (preDiff < 0) {
                    return new IndexInfo(withHeaders ? diff - 1 : diff, listTypes[i], false);
                } else {
                    diff = preDiff;
                }
            }
            return new IndexInfo(OUT_OF_RANGE, NO_TYPE, false);
        }

        public static class IndexInfo {

            public final int relativeIndex;
            public final int listType;
            public final boolean isHeader;

            public IndexInfo(int relativeIndex, int listType, boolean isHeader) {
                this.relativeIndex = relativeIndex;
                this.listType = listType;
                this.isHeader = isHeader;
            }

            @Override
            public String toString() {
                return "IndexInfo{" +
                        "relativeIndex=" + relativeIndex +
                        ", listType=" + listType +
                        ", isHeader=" + isHeader +
                        '}';
            }
        }

    }

    private static class IngredientViewHolder extends RecyclerView.ViewHolder {

        private final Context context;

        private final TextView textViewIngredientName;
        private final TextView textViewIngredientQuantityAndMeasureUnit;

        public IngredientViewHolder(final ViewGroup parent, final Context context, final LayoutInflater layoutInflater) {
            super(layoutInflater.inflate(R.layout.element_recipe_ingredient, parent, false));
            this.context = context;
            textViewIngredientName = itemView.findViewById(R.id.textview_recipe_ingredient_name);
            textViewIngredientQuantityAndMeasureUnit = itemView.findViewById(R.id.textview_recipe_ingredient_quantity);
        }

        public void bind(final GetRecipeIngredientsViewModel.Ingredient ingredient) {
            textViewIngredientName.setText(ingredient.getName());
            textViewIngredientQuantityAndMeasureUnit.setText(context.getString(R.string.recipe_detail_ingredient_quantity_and_unit, ingredient.getQuantity(), ingredient.getMeasureUnit()));
        }
    }

    private static class StepViewHolder extends RecyclerView.ViewHolder {

        private final Context context;

        private final TextView textViewStepName;
        private final ImageView imageViewStep;

        public StepViewHolder(final ViewGroup parent, final Context context, final LayoutInflater layoutInflater) {
            super(layoutInflater.inflate(R.layout.element_recipe_step, parent, false));
            this.context = context;
            textViewStepName = itemView.findViewById(R.id.textview_recipe_step_name);
            imageViewStep = itemView.findViewById(R.id.imageview_step);
        }

        public void bind(final int stepNumber, final GetRecipeStepsViewModel.Step step, final OnStepSelectedListener onStepSelectedListener, final Drawable defaultStepDrawable) {
            textViewStepName.setText(context.getString(R.string.recipe_detail_step_short_description, stepNumber, step.getShortDescription()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onStepSelectedListener != null) {
                        onStepSelectedListener.onStepSelected(step);
                    }
                }
            });
            final String stepPictureUrl = step.getPictureUrl();
            if (!stepPictureUrl.isEmpty()) {
                Picasso.with(context)
                        .load(stepPictureUrl)
                        .placeholder(defaultStepDrawable)
                        .error(defaultStepDrawable)
                        .into(imageViewStep);
            }
        }
    }

    private static class SectionHeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewSectionName;

        public SectionHeaderViewHolder(final ViewGroup parent, final LayoutInflater layoutInflater) {
            super(layoutInflater.inflate(R.layout.element_section_header, parent, false));
            textViewSectionName = itemView.findViewById(R.id.textview_section_header);
        }

        public void bind(final String sectionName) {
            textViewSectionName.setText(sectionName);
        }
    }

    public interface OnStepSelectedListener {

        void onStepSelected(final GetRecipeStepsViewModel.Step step);

    }
}
