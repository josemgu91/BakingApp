package com.josemgu91.bakingapp.android.ui;

import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeIngredientsController;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeIngredientsViewModel;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeStepsController;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeStepsViewModel;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipesController;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipesViewModel;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.View;

/**
 * Created by jose on 2/16/18.
 */

public interface ControllerFactory {


    GetRecipesController createGetRecipesController(final View<GetRecipesViewModel> getRecipesViewModel);

    GetRecipeIngredientsController createGetRecipeIngredientsController(final View<GetRecipeIngredientsViewModel> getRecipeIngredientsViewModel);

    GetRecipeStepsController createGetRecipeStepsController(final View<GetRecipeStepsViewModel> getRecipeStepsViewModel);

}
