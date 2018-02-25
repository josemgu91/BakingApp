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

package com.josemgu91.bakingapp.android.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.josemgu91.bakingapp.R;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeStepsViewModel;
import com.josemgu91.bakingapp.android.ui.recipe_detail.RecipeDetailFragment;
import com.josemgu91.bakingapp.android.ui.recipe_step_detail.RecipeStepDetailFragment;
import com.josemgu91.bakingapp.android.ui.recipes_list.RecipesFragment;

public class MainActivity extends AppCompatActivity implements RecipesFragment.OnRecipeSelectedListener, RecipeDetailFragment.OnStepSelectedListener {

    public final static String PARAM_RECIPE_ID = "com.josemgu91.bakingapp.RECIPE_ID";

    private final static String FRAGMENT_TAG_RECIPES_FRAGMENT = "recipes_fragment";
    private final static String FRAGMENT_TAG_RECIPE_DETAIL_FRAGMENT = "recipe_detail_fragment";
    private final static String FRAGMENT_TAG_RECIPE_STEP_DETAIL_FRAGMENT = "recipe_step_detail_fragment";

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
        fragmentManager.beginTransaction()
                .replace(R.id.fragment, RecipeStepDetailFragment.newInstance(step), FRAGMENT_TAG_RECIPE_STEP_DETAIL_FRAGMENT)
                .commit();
    }
}
