package com.josemgu91.bakingapp.android.ui.recipe_detail;

import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeIngredientsController;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeIngredientsViewModel;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeStepsController;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeStepsViewModel;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.View;
import com.josemgu91.bakingapp.android.ui.ControllerFactory;

/**
 * Created by jose on 2/16/18.
 */

public class RecipeDetailViewInterfaceAdapter {

    private final GetRecipeStepsController getRecipeStepsController;
    private final GetRecipeIngredientsController getRecipeIngredientsController;

    public RecipeDetailViewInterfaceAdapter(final ControllerFactory controllerFactory, final RecipeDetailViewInterface recipeDetailViewInterface) {
        this.getRecipeStepsController = controllerFactory.createGetRecipeStepsController(new View<GetRecipeStepsViewModel>() {
            @Override
            public void showResult(GetRecipeStepsViewModel getRecipeStepsViewModel) {
                recipeDetailViewInterface.showSteps(getRecipeStepsViewModel);
            }

            @Override
            public void showInProgress() {
                recipeDetailViewInterface.showStepsInProgress();
            }

            @Override
            public void showRetrieveError() {
                recipeDetailViewInterface.showStepsRetrieveError();
            }

            @Override
            public void showNoResult() {
                recipeDetailViewInterface.showStepsNoResult();
            }
        });
        this.getRecipeIngredientsController = controllerFactory.createGetRecipeIngredientsController(new View<GetRecipeIngredientsViewModel>() {
            @Override
            public void showResult(GetRecipeIngredientsViewModel getRecipeIngredientsViewModel) {
                recipeDetailViewInterface.showIngredients(getRecipeIngredientsViewModel);
            }

            @Override
            public void showInProgress() {
                recipeDetailViewInterface.showIngredientsInProgress();
            }

            @Override
            public void showRetrieveError() {
                recipeDetailViewInterface.showIngredientsRetrieveError();
            }

            @Override
            public void showNoResult() {
                recipeDetailViewInterface.showIngredientsNoResult();
            }
        });
    }

    public GetRecipeStepsController getGetRecipeStepsController() {
        return getRecipeStepsController;
    }

    public GetRecipeIngredientsController getGetRecipeIngredientsController() {
        return getRecipeIngredientsController;
    }
}
