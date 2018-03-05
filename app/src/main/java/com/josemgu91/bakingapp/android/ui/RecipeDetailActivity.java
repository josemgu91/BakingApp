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

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.josemgu91.bakingapp.R;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeStepsViewModel;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.MarkRecipeAsFavoriteController;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.MarkRecipeAsFavoriteViewModel;
import com.josemgu91.bakingapp.android.Application;
import com.josemgu91.bakingapp.android.ui.recipe_detail.RecipeDetailFragment;
import com.josemgu91.bakingapp.android.ui.recipe_step_detail.RecipeStepDetailFragment;
import com.josemgu91.bakingapp.android.widget.RecipesWidgetProvider;
import com.josemgu91.bakingapp.android.widget.RecipesWidgetService;
import com.josemgu91.bakingapp.domain.datagateways.DataGatewayException;

/**
 * Created by jose on 2/27/18.
 */

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnStepSelectedListener, RecipeDetailFragment.OnListScrolledListener {

    public static final String PARAM_RECIPE_ID = "com.josemgu91.bakingapp.RECIPE_ID";
    public static final String PARAM_RECIPE_NAME = "com.josemgu91.bakingapp.RECIPE_NAME";

    public final static String FRAGMENT_TAG_RECIPE_DETAIL_FRAGMENT = "recipe_detail_fragment";
    public final static String FRAGMENT_TAG_RECIPE_STEP_DETAIL_FRAGMENT = "recipe_step_detail_fragment";

    public final static String SAVED_INSTANCE_STATE_USE_SAVED_INSTANCE_STATE = "use_saved_instance_state";

    private FragmentManager fragmentManager;

    private boolean useSavedInstanceState = true;

    private FloatingActionButton floatingActionButtonMarkAsFavorite;

    private boolean isFavorite;
    private String recipeId;

    private MarkRecipeAsFavoriteController markRecipeAsFavoriteController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null && !savedInstanceState.getBoolean(SAVED_INSTANCE_STATE_USE_SAVED_INSTANCE_STATE)) {
            savedInstanceState = null;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        floatingActionButtonMarkAsFavorite = findViewById(R.id.floatingactionbutton_mark_as_favorite);
        markRecipeAsFavoriteController = createMarkAsFavoriteInteraction();
        floatingActionButtonMarkAsFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markAsFavoriteButtonClicked();
            }
        });
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getIntent().getStringExtra(PARAM_RECIPE_NAME));
        }
        fragmentManager = getSupportFragmentManager();
        fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentResumed(FragmentManager fm, Fragment f) {
                registerFragmentListener(f);
            }
        }, false);
        if (getIntent().hasExtra(PARAM_RECIPE_ID)) {
            recipeId = getIntent().getStringExtra(PARAM_RECIPE_ID);
            try {
                //TODO: This call shouldn't be in this layer.
                isFavorite = ((Application) getApplication()).getFavoriteRepositoryInstance().isFavorite(recipeId);
            } catch (DataGatewayException e) {
                e.printStackTrace();
            }
            setMarkAsFavoriteButtonIcon(isFavorite);
        } else {
            throw new IllegalStateException("Must have the RECIPE_ID parameter!");
        }
        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_pane_1, RecipeDetailFragment.newInstance(recipeId), FRAGMENT_TAG_RECIPE_DETAIL_FRAGMENT)
                    .commit();
        } else {
            final Fragment recipeStepDetailFragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG_RECIPE_STEP_DETAIL_FRAGMENT);
            if (recipeStepDetailFragment != null) {
                if (isInTwoPaneMode()) {
                    recipeStepDetailFragment.setUserVisibleHint(true);
                } else {
                    recipeStepDetailFragment.setUserVisibleHint(false);
                }
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        final String recipeId = intent.getStringExtra(PARAM_RECIPE_ID);
        final String recipeName = intent.getStringExtra(PARAM_RECIPE_NAME);
        if (recipeId != null &&
                recipeName != null &&
                !recipeId.equals(getIntent().getStringExtra(PARAM_RECIPE_ID)) &&
                !recipeName.equals(getIntent().getStringExtra(PARAM_RECIPE_NAME))) {
            getIntent().putExtra(PARAM_RECIPE_ID, recipeId);
            getIntent().putExtra(PARAM_RECIPE_NAME, recipeName);
            useSavedInstanceState = false;
            recreate();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_INSTANCE_STATE_USE_SAVED_INSTANCE_STATE, useSavedInstanceState);
    }

    private boolean isInTwoPaneMode() {
        return findViewById(R.id.fragment_pane_2) != null;
    }

    private void registerFragmentListener(final Fragment fragment) {
        switch (fragment.getTag()) {
            case FRAGMENT_TAG_RECIPE_DETAIL_FRAGMENT:
                ((RecipeDetailFragment) fragment).setOnStepSelectedListener(this);
                if (!isInTwoPaneMode()) {
                    ((RecipeDetailFragment) fragment).setOnListScrolledListener(this);
                }
                break;
        }
    }

    private void markAsFavoriteButtonClicked() {
        if (isFavorite) {
            markRecipeAsFavoriteController.unmarkAsFavorite(recipeId);
        } else {
            markRecipeAsFavoriteController.markAsFavorite(recipeId);
        }
    }

    private void setMarkAsFavoriteButtonIcon(final boolean isFavorite) {
        @DrawableRes final int iconDrawable = isFavorite
                ? R.drawable.ic_favorite_white_24dp : R.drawable.ic_favorite_border_white_24dp;
        floatingActionButtonMarkAsFavorite.setImageResource(iconDrawable);
    }

    private void updateLauncherWidget() {
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        final int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipesWidgetProvider.class));
        RecipesWidgetService.update(this, appWidgetIds);
    }

    private MarkRecipeAsFavoriteController createMarkAsFavoriteInteraction() {
        final com.josemgu91.bakingapp.adapter.presentation.ui.graphical.View<MarkRecipeAsFavoriteViewModel> markAsFavoriteView
                = new com.josemgu91.bakingapp.adapter.presentation.ui.graphical.View<MarkRecipeAsFavoriteViewModel>() {
            @Override
            public void showResult(MarkRecipeAsFavoriteViewModel markRecipeAsFavoriteViewModel) {
                final boolean markedAsFavorite = markRecipeAsFavoriteViewModel.isMarkedAsFavorite();
                isFavorite = markedAsFavorite;
                setMarkAsFavoriteButtonIcon(markedAsFavorite);
                updateLauncherWidget();
            }

            @Override
            public void showInProgress() {

            }

            @Override
            public void showError() {
                Snackbar.make(floatingActionButtonMarkAsFavorite, R.string.recipe_detail_mark_as_favorite_error, Snackbar.LENGTH_SHORT).show();
            }
        };
        return new ControllerFactoryImpl(this).createMarkRecipeAsFavoriteController(markAsFavoriteView);
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

    @Override
    public void onRecipeDetailListScrolled(int dx, int dy) {
        if (dy > 0 && floatingActionButtonMarkAsFavorite.getVisibility() == View.VISIBLE) {
            floatingActionButtonMarkAsFavorite.hide();
        } else if (dy < 0 && floatingActionButtonMarkAsFavorite.getVisibility() != View.VISIBLE) {
            floatingActionButtonMarkAsFavorite.show();
        }
    }
}
