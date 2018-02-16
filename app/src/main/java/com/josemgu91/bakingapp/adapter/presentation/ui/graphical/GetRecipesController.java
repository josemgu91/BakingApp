package com.josemgu91.bakingapp.adapter.presentation.ui.graphical;

import com.josemgu91.bakingapp.domain.datagateways.RecipeDataGateway;
import com.josemgu91.bakingapp.domain.usecases.GetRecipes;
import com.josemgu91.bakingapp.domain.usecases.RecipeOutput;
import com.josemgu91.bakingapp.domain.usecases.common.GetUseCaseOutput;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by jose on 2/14/18.
 */

public class GetRecipesController {

    private final GetUseCaseOutput<List<RecipeOutput>> getRecipesUseCaseOutput;
    private final RecipeDataGateway recipeDataGateway;
    private final Executor controllerExecutor;

    public GetRecipesController(GetUseCaseOutput<List<RecipeOutput>> getRecipesUseCaseOutput, RecipeDataGateway recipeDataGateway, Executor controllerExecutor) {
        this.getRecipesUseCaseOutput = getRecipesUseCaseOutput;
        this.recipeDataGateway = recipeDataGateway;
        this.controllerExecutor = controllerExecutor;
    }

    public void getRecipes() {
        controllerExecutor.execute(new Runnable() {
            @Override
            public void run() {
                new GetRecipes(getRecipesUseCaseOutput, recipeDataGateway).execute();
            }
        });
    }

}
