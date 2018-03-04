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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.josemgu91.bakingapp.R;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipesController;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipesViewModel;
import com.josemgu91.bakingapp.android.test.SimpleIdlingResource;
import com.josemgu91.bakingapp.android.ui.ControllerFactoryImpl;

/**
 * Created by jose on 2/15/18.
 */

public class RecipesFragment extends Fragment implements com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetView<GetRecipesViewModel> {

    private static final String PARAM_USE_GRID_LAYOUT = "use_grid_layout";

    private boolean useGridLayout;

    private RecipesRecyclerViewAdapter recipesRecyclerViewAdapter;

    private OnRecipeSelectedListener onRecipeSelectedListener;

    private RecyclerView recyclerViewRecipes;
    private View progressBarRecipesRetrievingProgress;
    private View errorViewErrorMessage;

    private GetRecipesController getRecipesController;

    public void setOnRecipeSelectedListener(OnRecipeSelectedListener onRecipeSelectedListener) {
        this.onRecipeSelectedListener = onRecipeSelectedListener;
    }

    public static RecipesFragment newInstance(final boolean useGridLayout) {
        RecipesFragment fragment = new RecipesFragment();
        final Bundle arguments = new Bundle();
        arguments.putBoolean(PARAM_USE_GRID_LAYOUT, useGridLayout);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    private SimpleIdlingResource simpleIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (simpleIdlingResource == null) {
            simpleIdlingResource = new SimpleIdlingResource();
        }
        return simpleIdlingResource;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleIdlingResource = (SimpleIdlingResource) getIdlingResource();
        simpleIdlingResource.setIdleState(false);
        final Bundle arguments = getArguments();
        if (!arguments.containsKey(PARAM_USE_GRID_LAYOUT)) {
            throw new RuntimeException("Use the newInstance() static method instead the standard constructor!");
        }
        useGridLayout = arguments.getBoolean(PARAM_USE_GRID_LAYOUT);
        getRecipesController = new ControllerFactoryImpl(getActivity()).createGetRecipesController(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        progressBarRecipesRetrievingProgress = view.findViewById(R.id.progressbar_recipes_retrieving_progress);
        errorViewErrorMessage = view.findViewById(R.id.errorview_error_message);
        recyclerViewRecipes = view.findViewById(R.id.recyclerview_recipes);
        recipesRecyclerViewAdapter = new RecipesRecyclerViewAdapter(getActivity(), inflater);
        recipesRecyclerViewAdapter.setOnRecipeSelectedListener(new RecipesRecyclerViewAdapter.OnRecipeSelectedListener() {
            @Override
            public void onRecipeSelected(GetRecipesViewModel.Recipe recipe) {
                if (onRecipeSelectedListener != null) {
                    onRecipeSelectedListener.onRecipeSelected(recipe);
                }
            }
        });
        if (useGridLayout) {
            recyclerViewRecipes.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        } else {
            recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        recyclerViewRecipes.setAdapter(recipesRecyclerViewAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showInProgress();
        getRecipesController.getRecipes();
    }

    @Override
    public void showResult(final GetRecipesViewModel getRecipesViewModel) {
        progressBarRecipesRetrievingProgress.setVisibility(View.GONE);
        errorViewErrorMessage.setVisibility(View.GONE);
        recyclerViewRecipes.setVisibility(View.VISIBLE);
        recipesRecyclerViewAdapter.setRecipes(getRecipesViewModel.getRecipes());
        simpleIdlingResource.setIdleState(true);
    }

    @Override
    public void showInProgress() {
        progressBarRecipesRetrievingProgress.setVisibility(View.VISIBLE);
        errorViewErrorMessage.setVisibility(View.GONE);
        recyclerViewRecipes.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        progressBarRecipesRetrievingProgress.setVisibility(View.GONE);
        errorViewErrorMessage.setVisibility(View.VISIBLE);
        recyclerViewRecipes.setVisibility(View.GONE);
    }

    @Override
    public void showNoResult() {
        progressBarRecipesRetrievingProgress.setVisibility(View.GONE);
        errorViewErrorMessage.setVisibility(View.GONE);
        recyclerViewRecipes.setVisibility(View.VISIBLE);
    }

    public interface OnRecipeSelectedListener {

        void onRecipeSelected(final GetRecipesViewModel.Recipe recipe);

    }

}
