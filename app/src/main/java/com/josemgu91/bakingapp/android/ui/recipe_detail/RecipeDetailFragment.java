package com.josemgu91.bakingapp.android.ui.recipe_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.josemgu91.bakingapp.R;

/**
 * Created by jose on 2/15/18.
 */

public class RecipeDetailFragment extends Fragment {

    public final static String PARAM_RECIPE_ID = "recipe_id";

    public static RecipeDetailFragment newInstance(final String recipeId) {
        final RecipeDetailFragment fragment = new RecipeDetailFragment();
        final Bundle arguments = new Bundle();
        arguments.putString(PARAM_RECIPE_ID, recipeId);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        return view;
    }
}
