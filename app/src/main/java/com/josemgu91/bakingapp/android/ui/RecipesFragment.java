package com.josemgu91.bakingapp.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.josemgu91.bakingapp.R;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipesController;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipesViewModel;

/**
 * Created by jose on 2/15/18.
 */

public class RecipesFragment extends Fragment implements com.josemgu91.bakingapp.adapter.presentation.ui.graphical.View<GetRecipesViewModel> {

    private RecipesRecyclerViewAdapter recipesRecyclerViewAdapter;

    private OnRecipeSelectedListener onRecipeSelectedListener;

    public void setOnRecipeSelectedListener(OnRecipeSelectedListener onRecipeSelectedListener) {
        this.onRecipeSelectedListener = onRecipeSelectedListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final GetRecipesController getGetRecipesController = new ControllerFactoryImpl(getActivity()).createGetRecipesController(this);
        getGetRecipesController.getRecipes();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        final RecyclerView recyclerViewRecipes = view.findViewById(R.id.recyclerview_recipes);
        recipesRecyclerViewAdapter = new RecipesRecyclerViewAdapter(inflater);
        recipesRecyclerViewAdapter.setOnRecipeSelectedListener(new RecipesRecyclerViewAdapter.OnRecipeSelectedListener() {
            @Override
            public void onRecipeSelected(GetRecipesViewModel.Recipe recipe) {
                if (onRecipeSelectedListener != null) {
                    onRecipeSelectedListener.onRecipeSelected(recipe.getId());
                }
            }
        });
        recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewRecipes.setAdapter(recipesRecyclerViewAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void showResult(final GetRecipesViewModel getRecipesViewModel) {
        recipesRecyclerViewAdapter.setRecipes(getRecipesViewModel.getRecipes());
    }

    @Override
    public void showInProgress() {

    }

    @Override
    public void showRetrieveError() {

    }

    @Override
    public void showNoResult() {

    }

    public interface OnRecipeSelectedListener {

        void onRecipeSelected(final String recipeId);

    }

}
