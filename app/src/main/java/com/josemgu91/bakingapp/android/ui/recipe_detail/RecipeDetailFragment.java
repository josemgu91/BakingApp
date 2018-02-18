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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.josemgu91.bakingapp.R;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeIngredientsViewModel;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeStepsViewModel;
import com.josemgu91.bakingapp.android.ui.ControllerFactory;
import com.josemgu91.bakingapp.android.ui.ControllerFactoryImpl;

/**
 * Created by jose on 2/15/18.
 */

public class RecipeDetailFragment extends Fragment implements RecipeDetailViewInterface {

    public final static String PARAM_RECIPE_ID = "recipe_id";

    public static RecipeDetailFragment newInstance(@NonNull final String recipeId) {
        final RecipeDetailFragment fragment = new RecipeDetailFragment();
        final Bundle arguments = new Bundle();
        arguments.putString(PARAM_RECIPE_ID, recipeId);
        fragment.setArguments(arguments);
        return fragment;
    }

    private RecipeDetailRecyclerViewAdapter recipeDetailRecyclerViewAdapter;

    private OnStepSelectedListener onStepSelectedListener;

    public void setOnStepSelectedListener(OnStepSelectedListener onStepSelectedListener) {
        this.onStepSelectedListener = onStepSelectedListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String recipeId = getArguments().getString(PARAM_RECIPE_ID);
        if (recipeId == null) {
            throw new RuntimeException("Use the newInstance() static method instead the standard constructor!");
        }
        final ControllerFactory controllerFactory = new ControllerFactoryImpl(getActivity());
        final RecipeDetailViewInterfaceAdapter recipeDetailViewInterfaceAdapter = new RecipeDetailViewInterfaceAdapter(controllerFactory, this);
        recipeDetailViewInterfaceAdapter.getGetRecipeIngredientsController().getIngredients(recipeId);
        recipeDetailViewInterfaceAdapter.getGetRecipeStepsController().getSteps(recipeId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        final RecyclerView recyclerViewRecipeDetail = view.findViewById(R.id.recyclerview_recipe_detail);
        recipeDetailRecyclerViewAdapter = new RecipeDetailRecyclerViewAdapter(inflater);
        recipeDetailRecyclerViewAdapter.setOnStepSelectedListener(new RecipeDetailRecyclerViewAdapter.OnStepSelectedListener() {
            @Override
            public void onStepSelected(GetRecipeStepsViewModel.Step step) {
                if (onStepSelectedListener != null) {
                    onStepSelectedListener.onStepSelected(step);
                }
            }
        });
        recyclerViewRecipeDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewRecipeDetail.setAdapter(recipeDetailRecyclerViewAdapter);
        return view;
    }

    @Override
    public void showIngredients(GetRecipeIngredientsViewModel ingredientsViewModel) {
        recipeDetailRecyclerViewAdapter.setIngredients(ingredientsViewModel.getIngredients());
    }

    @Override
    public void showSteps(GetRecipeStepsViewModel stepsViewModel) {
        recipeDetailRecyclerViewAdapter.setSteps(stepsViewModel.getSteps());
    }

    @Override
    public void showStepsInProgress() {

    }

    @Override
    public void showIngredientsInProgress() {

    }

    @Override
    public void showStepsRetrieveError() {

    }

    @Override
    public void showIngredientsRetrieveError() {

    }

    @Override
    public void showStepsNoResult() {

    }

    @Override
    public void showIngredientsNoResult() {

    }

    public interface OnStepSelectedListener {

        void onStepSelected(GetRecipeStepsViewModel.Step step);

    }

}
