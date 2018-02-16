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
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipesPresenter;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipesViewModel;
import com.josemgu91.bakingapp.data.remote.RemoteRetrofitRepository;

/**
 * Created by jose on 2/15/18.
 */

public class RecipesFragment extends Fragment implements com.josemgu91.bakingapp.adapter.presentation.ui.graphical.View<GetRecipesViewModel> {

    private GetRecipesController getRecipesController;

    private RecipesRecyclerViewAdapter recipesRecyclerViewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final GetRecipesPresenter getRecipesPresenter = new GetRecipesPresenter(this);
        final RemoteRetrofitRepository remoteRetrofitRepository = new RemoteRetrofitRepository();
        getRecipesController = new GetRecipesController(getRecipesPresenter, remoteRetrofitRepository);
        new Thread(new Runnable() { //TODO: I think that an executor interface in the adapter layer would be a good idea.
            @Override
            public void run() {
                getRecipesController.getRecipes();
            }
        }).start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_recipes, container, true);
        final RecyclerView recyclerViewRecipes = view.findViewById(R.id.recyclerview_recipes);
        recipesRecyclerViewAdapter = new RecipesRecyclerViewAdapter(inflater);
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recipesRecyclerViewAdapter.setRecipes(getRecipesViewModel.getRecipes());
            }
        });
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

}
