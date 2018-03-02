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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.josemgu91.bakingapp.R;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeStepsViewModel;
import com.josemgu91.bakingapp.android.ui.recipe_detail.RecipeDetailFragment;
import com.josemgu91.bakingapp.android.ui.recipe_step_detail.RecipeStepDetailFragment;

/**
 * Created by jose on 2/27/18.
 */

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnStepSelectedListener {

    public static final String PARAM_RECIPE_ID = "com.josemgu91.bakingapp.RECIPE_ID";

    public final static String FRAGMENT_TAG_RECIPE_DETAIL_FRAGMENT = "recipe_detail_fragment";
    public final static String FRAGMENT_TAG_RECIPE_STEP_DETAIL_FRAGMENT = "recipe_step_detail_fragment";

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentResumed(FragmentManager fm, Fragment f) {
                registerFragmentListener(f);
            }
        }, false);
        final Fragment recipeDetailFragment;
        if (savedInstanceState == null) {
            if (getIntent().hasExtra(PARAM_RECIPE_ID)) {
                final String recipeId = getIntent().getStringExtra(PARAM_RECIPE_ID);
                recipeDetailFragment = RecipeDetailFragment.newInstance(recipeId);
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_pane_1, recipeDetailFragment, FRAGMENT_TAG_RECIPE_DETAIL_FRAGMENT)
                        .commit();
            } else {
                throw new IllegalStateException("Must have the RECIPE_ID parameter!");
            }
        }
    }

    private boolean isInTwoPaneMode() {
        return findViewById(R.id.fragment_pane_2) != null;
    }

    private void registerFragmentListener(final Fragment fragment) {
        switch (fragment.getTag()) {
            case FRAGMENT_TAG_RECIPE_DETAIL_FRAGMENT:
                ((RecipeDetailFragment) fragment).setOnStepSelectedListener(this);
                break;
        }
    }

    @Override
    public void onStepSelected(GetRecipeStepsViewModel.Step step) {
        if (isInTwoPaneMode()) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_pane_2, RecipeStepDetailFragment.newInstance(step), FRAGMENT_TAG_RECIPE_STEP_DETAIL_FRAGMENT)
                    .commit();
        } else {
            RecipeStepDetailActivity.start(this, step);
        }
    }
}
