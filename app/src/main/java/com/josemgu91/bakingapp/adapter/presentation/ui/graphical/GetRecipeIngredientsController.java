package com.josemgu91.bakingapp.adapter.presentation.ui.graphical;

import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.common.AbstractController;
import com.josemgu91.bakingapp.domain.datagateways.IngredientDataGateway;
import com.josemgu91.bakingapp.domain.usecases.GetRecipeIngredients;
import com.josemgu91.bakingapp.domain.usecases.IngredientOutput;
import com.josemgu91.bakingapp.domain.usecases.common.GetUseCaseOutput;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by jose on 2/16/18.
 */

public class GetRecipeIngredientsController extends AbstractController {

    private final GetUseCaseOutput<List<IngredientOutput>> getRecipeIngredientsUseCaseOutput;
    private final IngredientDataGateway ingredientDataGateway;

    public GetRecipeIngredientsController(Executor controllerExecutor, GetUseCaseOutput<List<IngredientOutput>> getRecipeIngredientsUseCaseOutput, IngredientDataGateway ingredientDataGateway) {
        super(controllerExecutor);
        this.getRecipeIngredientsUseCaseOutput = getRecipeIngredientsUseCaseOutput;
        this.ingredientDataGateway = ingredientDataGateway;
    }

    public void getIngredients(final String recipeId) {
        executeInControllerExecutor(new Runnable() {
            @Override
            public void run() {
                new GetRecipeIngredients(getRecipeIngredientsUseCaseOutput, ingredientDataGateway, recipeId).execute();
            }
        });
    }
}
