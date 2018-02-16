package com.josemgu91.bakingapp.android.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.josemgu91.bakingapp.R;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeStepsViewModel;
import com.josemgu91.bakingapp.android.ui.recipe_detail.RecipeDetailFragment;
import com.josemgu91.bakingapp.android.ui.recipes_list.RecipesFragment;

public class MainActivity extends AppCompatActivity implements RecipesFragment.OnRecipeSelectedListener, RecipeDetailFragment.OnStepSelectedListener {

    private final static String FRAGMENT_TAG_RECIPES_FRAGMENT = "recipes_fragment";
    private final static String FRAGMENT_TAG_RECIPE_DETAIL_FRAGMENT = "recipe_detail_fragment";

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentResumed(FragmentManager fm, Fragment f) {
                registerFragmentListener(f);
            }
        }, false);
        if (savedInstanceState == null) {
            final RecipesFragment recipesFragment = new RecipesFragment();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment, recipesFragment, FRAGMENT_TAG_RECIPES_FRAGMENT)
                    .commit();
        }
    }

    private void registerFragmentListener(final Fragment fragment) {
        switch (fragment.getTag()) {
            case FRAGMENT_TAG_RECIPES_FRAGMENT:
                ((RecipesFragment) fragment).setOnRecipeSelectedListener(this);
                break;
            case FRAGMENT_TAG_RECIPE_DETAIL_FRAGMENT:
                ((RecipeDetailFragment) fragment).setOnStepSelectedListener(this);
                break;
        }
    }

    @Override
    public void onRecipeSelected(String recipeId) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment, RecipeDetailFragment.newInstance(recipeId), FRAGMENT_TAG_RECIPE_DETAIL_FRAGMENT)
                .commit();
    }

    @Override
    public void onStepSelected(GetRecipeStepsViewModel.Step step) {
        Toast.makeText(this, step.getShortDescription(), Toast.LENGTH_SHORT).show();
    }
}
