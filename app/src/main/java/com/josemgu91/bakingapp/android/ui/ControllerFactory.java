package com.josemgu91.bakingapp.android.ui;

import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipesController;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipesViewModel;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.View;

/**
 * Created by jose on 2/16/18.
 */

public interface ControllerFactory {


    GetRecipesController createGetRecipesController(final View<GetRecipesViewModel> getRecipesView);

    //GetRecipesController createGetRecipeIngredientsController(final View<GetRecipesViewModel> getRecipesView);

    //GetRecipesController createGetRecipeStepsController(final View<GetRecipesViewModel> getRecipesView);

}
