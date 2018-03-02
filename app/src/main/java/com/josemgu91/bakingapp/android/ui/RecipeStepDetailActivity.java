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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.josemgu91.bakingapp.R;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeStepsViewModel;
import com.josemgu91.bakingapp.android.ui.recipe_step_detail.RecipeStepDetailFragment;

/**
 * Created by jose on 3/1/18.
 */

public class RecipeStepDetailActivity extends AppCompatActivity {

    public static final String PARAM_RECIPE_STEP_DESCRIPTION = "com.josemgu91.bakingapp.RECIPE_STEP_DESCRIPTION";
    public static final String PARAM_RECIPE_STEP_SHORT_DESCRIPTION = "com.josemgu91.bakingapp.RECIPE_STEP_SHORT_DESCRIPTION";
    public static final String PARAM_RECIPE_STEP_VIDEO_URL = "com.josemgu91.bakingapp.RECIPE_STEP_VIDEO_URL";
    public static final String PARAM_RECIPE_STEP_PICTURE_THUMBNAIL_URL = "com.josemgu91.bakingapp.STEP_PICTURE_THUMBNAIL_URL";

    public static void start(Context context, final GetRecipeStepsViewModel.Step step) {
        final Intent starter = new Intent(context, RecipeStepDetailActivity.class);
        starter.putExtra(PARAM_RECIPE_STEP_DESCRIPTION, step.getLongDescription());
        starter.putExtra(PARAM_RECIPE_STEP_SHORT_DESCRIPTION, step.getShortDescription());
        starter.putExtra(PARAM_RECIPE_STEP_VIDEO_URL, step.getVideoUrl());
        starter.putExtra(PARAM_RECIPE_STEP_PICTURE_THUMBNAIL_URL, step.getPictureUrl());
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            final GetRecipeStepsViewModel.Step step = getRecipeStepDetailFromIntent();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment, RecipeStepDetailFragment.newInstance(step))
                    .commit();
        }
    }

    private GetRecipeStepsViewModel.Step getRecipeStepDetailFromIntent() {
        if (getIntent().hasExtra(PARAM_RECIPE_STEP_DESCRIPTION) &&
                getIntent().hasExtra(PARAM_RECIPE_STEP_SHORT_DESCRIPTION) &&
                getIntent().hasExtra(PARAM_RECIPE_STEP_VIDEO_URL) &&
                getIntent().hasExtra(PARAM_RECIPE_STEP_PICTURE_THUMBNAIL_URL)) {
            return new GetRecipeStepsViewModel.Step(
                    getIntent().getStringExtra(PARAM_RECIPE_STEP_SHORT_DESCRIPTION),
                    getIntent().getStringExtra(PARAM_RECIPE_STEP_DESCRIPTION),
                    getIntent().getStringExtra(PARAM_RECIPE_STEP_PICTURE_THUMBNAIL_URL),
                    getIntent().getStringExtra(PARAM_RECIPE_STEP_VIDEO_URL)
            );
        } else {
            throw new IllegalStateException("Some RecipeStepDetailActivity intent extras missing! Use the static start method.");
        }
    }
}
