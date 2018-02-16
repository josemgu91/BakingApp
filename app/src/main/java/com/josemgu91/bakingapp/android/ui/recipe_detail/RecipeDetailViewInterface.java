package com.josemgu91.bakingapp.android.ui.recipe_detail;

import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeIngredientsViewModel;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeStepsViewModel;

/**
 * Created by jose on 2/16/18.
 */

public interface RecipeDetailViewInterface {

    void showIngredients(final GetRecipeIngredientsViewModel ingredientsViewModel);

    void showSteps(final GetRecipeStepsViewModel stepsViewModel);

    void showStepsInProgress();

    void showIngredientsInProgress();

    void showStepsRetrieveError();

    void showIngredientsRetrieveError();

    void showStepsNoResult();

    void showIngredientsNoResult();
}
