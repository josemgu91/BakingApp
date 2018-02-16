package com.josemgu91.bakingapp.adapter.presentation.ui.graphical;

import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.common.AbstractController;
import com.josemgu91.bakingapp.domain.datagateways.RecipeDataGateway;
import com.josemgu91.bakingapp.domain.usecases.GetRecipes;
import com.josemgu91.bakingapp.domain.usecases.RecipeOutput;
import com.josemgu91.bakingapp.domain.usecases.common.GetUseCaseOutput;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by jose on 2/14/18.
 */

public class GetRecipesController extends AbstractController {

    private final GetRecipes getRecipesUseCase;

    public GetRecipesController(Executor controllerExecutor, GetUseCaseOutput<List<RecipeOutput>> getRecipesUseCaseOutput, RecipeDataGateway recipeDataGateway) {
        super(controllerExecutor);
        getRecipesUseCase = new GetRecipes(getRecipesUseCaseOutput, recipeDataGateway);
    }

    public void getRecipes() {
        executeInControllerExecutor(new Runnable() {
            @Override
            public void run() {
                getRecipesUseCase.execute();
            }
        });
    }

}
