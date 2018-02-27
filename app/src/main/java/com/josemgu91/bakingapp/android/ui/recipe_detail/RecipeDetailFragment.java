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

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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

    private RecyclerView recyclerViewRecipeDetail;
    private View progressBarRecipeDetailRetrievingProgress;
    private View errorViewErrorMessage;

    private AtomicBoolean atomicBooleanHasProgressStateTriggered = new AtomicBoolean(false);
    private AtomicBoolean atomicBooleanHasErrorStateTriggered = new AtomicBoolean(false);
    private AtomicInteger atomicIntegerResultCount = new AtomicInteger(0);

    private RecipeDetailViewInterfaceAdapter recipeDetailViewInterfaceAdapter;

    public void setOnStepSelectedListener(OnStepSelectedListener onStepSelectedListener) {
        this.onStepSelectedListener = onStepSelectedListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ControllerFactory controllerFactory = new ControllerFactoryImpl(getActivity());
        recipeDetailViewInterfaceAdapter = new RecipeDetailViewInterfaceAdapter(controllerFactory, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        recyclerViewRecipeDetail = view.findViewById(R.id.recyclerview_recipe_detail);
        progressBarRecipeDetailRetrievingProgress = view.findViewById(R.id.progressbar_recipe_details_retrieving_progress);
        errorViewErrorMessage = view.findViewById(R.id.errorview_error_message);
        recipeDetailRecyclerViewAdapter = new RecipeDetailRecyclerViewAdapter(getActivity(), inflater);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showProgress();
        final String recipeId = getArguments().getString(PARAM_RECIPE_ID);
        if (recipeId == null) {
            throw new RuntimeException("Use the newInstance() static method instead the standard constructor!");
        }
        recipeDetailViewInterfaceAdapter.getGetRecipeIngredientsController().getIngredients(recipeId);
        recipeDetailViewInterfaceAdapter.getGetRecipeStepsController().getSteps(recipeId);
    }

    @Override
    public void showIngredients(GetRecipeIngredientsViewModel ingredientsViewModel) {
        showSyncedResult();
        recipeDetailRecyclerViewAdapter.setIngredients(ingredientsViewModel.getIngredients());
    }

    @Override
    public void showSteps(GetRecipeStepsViewModel stepsViewModel) {
        showSyncedResult();
        recipeDetailRecyclerViewAdapter.setSteps(stepsViewModel.getSteps());
    }

    @Override
    public void showStepsInProgress() {
        showSyncedInProgress();
    }

    @Override
    public void showIngredientsInProgress() {
        showSyncedInProgress();
    }

    @Override
    public void showStepsRetrieveError() {
        showSyncedRetrieveError();
    }

    @Override
    public void showIngredientsRetrieveError() {
        showSyncedRetrieveError();
    }

    @Override
    public void showStepsNoResult() {
        showSyncedResult();
    }

    @Override
    public void showIngredientsNoResult() {
        showSyncedResult();
    }

    private void showSyncedInProgress() {
        if (!atomicBooleanHasProgressStateTriggered.get()) {
            atomicBooleanHasProgressStateTriggered.set(true);
            showProgress();
        }
    }

    private void showSyncedRetrieveError() {
        if (!atomicBooleanHasErrorStateTriggered.get()) {
            atomicBooleanHasErrorStateTriggered.set(true);
            showError();
        }
    }

    private void showSyncedResult() {
        if (atomicIntegerResultCount.get() == 1) {
            showResult();
        } else {
            atomicIntegerResultCount.addAndGet(1);
        }
    }

    private void showProgress() {
        progressBarRecipeDetailRetrievingProgress.setVisibility(View.VISIBLE);
        errorViewErrorMessage.setVisibility(View.GONE);
        recyclerViewRecipeDetail.setVisibility(View.GONE);
    }

    private void showError() {
        progressBarRecipeDetailRetrievingProgress.setVisibility(View.GONE);
        errorViewErrorMessage.setVisibility(View.VISIBLE);
        recyclerViewRecipeDetail.setVisibility(View.GONE);
    }

    private void showResult() {
        progressBarRecipeDetailRetrievingProgress.setVisibility(View.GONE);
        errorViewErrorMessage.setVisibility(View.GONE);
        recyclerViewRecipeDetail.setVisibility(View.VISIBLE);
    }

    public interface OnStepSelectedListener {

        void onStepSelected(GetRecipeStepsViewModel.Step step);

    }

}
