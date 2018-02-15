package com.josemgu91.bakingapp.adapter.presentation.ui.graphical;

import com.josemgu91.bakingapp.domain.datagateways.RecipeDataGateway;
import com.josemgu91.bakingapp.domain.usecases.GetRecipes;
import com.josemgu91.bakingapp.domain.usecases.RecipeOutput;
import com.josemgu91.bakingapp.domain.usecases.common.GetUseCaseOutput;

import java.util.List;

/**
 * Created by jose on 2/14/18.
 */

public class GetRecipesController {

    private final GetUseCaseOutput<List<RecipeOutput>> getRecipesUseCaseOutput;
    private final RecipeDataGateway recipeDataGateway;

    public GetRecipesController(GetUseCaseOutput<List<RecipeOutput>> getRecipesUseCaseOutput, RecipeDataGateway recipeDataGateway) {
        this.getRecipesUseCaseOutput = getRecipesUseCaseOutput;
        this.recipeDataGateway = recipeDataGateway;
    }

    public void getRecipes() {
        new GetRecipes(getRecipesUseCaseOutput, recipeDataGateway).execute();
    }

}
