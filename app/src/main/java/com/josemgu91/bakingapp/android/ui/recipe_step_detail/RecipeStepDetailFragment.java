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

package com.josemgu91.bakingapp.android.ui.recipe_step_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.josemgu91.bakingapp.R;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeStepsViewModel;

/**
 * Created by jose on 2/15/18.
 */

public class RecipeStepDetailFragment extends Fragment {

    private View viewRecipeStepVideo;
    private TextView textViewRecipeStep;

    private static final String PARAM_RECIPE_STEP_DESCRIPTION = "recipe_step_description";
    private static final String PARAM_RECIPE_STEP_VIDEO_URL = "recipe_step_video_url";
    private static final String PARAM_RECIPE_STEP_PICTURE_THUMBNAIL_URL = "recipe_step_picture_thumbnail_url";

    private String recipeStepDescription;
    private String recipeStepVideoUrl;
    private String recipeStepPictureThumbnailUrl;

    /*
     * TODO: Maybe I can make a local Android parcelable view model,
     * but I think that for the moment this is good enough.
     */
    public static RecipeStepDetailFragment newInstance(final GetRecipeStepsViewModel.Step recipeStep) {
        final RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        final Bundle arguments = new Bundle();
        arguments.putString(PARAM_RECIPE_STEP_DESCRIPTION, recipeStep.getLongDescription());
        arguments.putString(PARAM_RECIPE_STEP_VIDEO_URL, recipeStep.getVideoUrl());
        arguments.putString(PARAM_RECIPE_STEP_PICTURE_THUMBNAIL_URL, recipeStep.getPictureUrl());
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle arguments = getArguments();
        if (!arguments.containsKey(PARAM_RECIPE_STEP_DESCRIPTION)) {
            throw new RuntimeException("Use the newInstance() static method instead the standard constructor!");
        }
        recipeStepDescription = arguments.getString(PARAM_RECIPE_STEP_DESCRIPTION);
        recipeStepVideoUrl = arguments.getString(PARAM_RECIPE_STEP_VIDEO_URL);
        recipeStepPictureThumbnailUrl = arguments.getString(PARAM_RECIPE_STEP_PICTURE_THUMBNAIL_URL);
        recipeStepDescription = recipeStepDescription != null ? recipeStepDescription : "";
        recipeStepVideoUrl = recipeStepVideoUrl != null ? recipeStepDescription : "";
        recipeStepPictureThumbnailUrl = recipeStepPictureThumbnailUrl != null ? recipeStepDescription : "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
        viewRecipeStepVideo = view.findViewById(R.id.view_recipe_step_video);
        textViewRecipeStep = view.findViewById(R.id.textview_recipe_step);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        textViewRecipeStep.setText(recipeStepDescription);
    }
}
