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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.josemgu91.bakingapp.R;
import com.josemgu91.bakingapp.android.ui.recipes_list.RecipesFragment;

public class RecipesListActivity extends AppCompatActivity implements RecipesFragment.OnRecipeSelectedListener {

    public final static String PARAM_RECIPE_ID = "com.josemgu91.bakingapp.RECIPE_ID";

    public final static String FRAGMENT_TAG_RECIPES_FRAGMENT = "recipes_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentResumed(FragmentManager fm, Fragment f) {
                registerFragmentListener(f);
            }
        }, false);
        if (savedInstanceState == null) {
            final RecipesFragment recipesFragment = RecipesFragment.newInstance(isTablet());
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment, recipesFragment, FRAGMENT_TAG_RECIPES_FRAGMENT)
                    .commit();
        }
        processIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processIntent(intent);
    }

    private void processIntent(final Intent intent) {
        if (intent.hasExtra(PARAM_RECIPE_ID)) {
            onRecipeSelected(intent.getStringExtra(PARAM_RECIPE_ID));
            getIntent().removeExtra(PARAM_RECIPE_ID);
        }
    }

    private boolean isTablet() {
        return getResources().getConfiguration().smallestScreenWidthDp >= 600;
    }

    private void registerFragmentListener(final Fragment fragment) {
        switch (fragment.getTag()) {
            case FRAGMENT_TAG_RECIPES_FRAGMENT:
                ((RecipesFragment) fragment).setOnRecipeSelectedListener(this);
                break;
        }
    }

    @Override
    public void onRecipeSelected(String recipeId) {
        startActivity(
                new Intent(this, RecipeDetailActivity.class)
                        .putExtra(RecipeDetailActivity.PARAM_RECIPE_ID, recipeId)
        );
    }

}
