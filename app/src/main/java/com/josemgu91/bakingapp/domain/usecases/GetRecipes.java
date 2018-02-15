package com.josemgu91.bakingapp.domain.usecases;

import com.josemgu91.bakingapp.domain.datagateways.DataGatewayException;
import com.josemgu91.bakingapp.domain.datagateways.RecipeDataGateway;
import com.josemgu91.bakingapp.domain.entities.Recipe;
import com.josemgu91.bakingapp.domain.usecases.common.AbstractGetUseCase;
import com.josemgu91.bakingapp.domain.usecases.common.ListMapper;
import com.josemgu91.bakingapp.domain.usecases.common.OutputMapper;
import com.josemgu91.bakingapp.domain.usecases.common.GetUseCaseOutput;

import java.util.List;

/**
 * Created by jose on 2/14/18.
 */

public class GetRecipes extends AbstractGetUseCase<List<Recipe>, List<RecipeOutput>> {

    private final RecipeDataGateway recipeDataGateway;

    public GetRecipes(GetUseCaseOutput<List<RecipeOutput>> getUseCaseOutput, RecipeDataGateway recipeDataGateway) {
        super(getUseCaseOutput, new ListMapper<>(new RecipeMapper()));
        this.recipeDataGateway = recipeDataGateway;
    }

    @Override
    protected List<Recipe> getData() throws DataGatewayException {
        return recipeDataGateway.getRecipes();
    }

    private static class RecipeMapper implements OutputMapper<Recipe, RecipeOutput> {

        @Override
        public RecipeOutput map(Recipe recipe) {
            return new RecipeOutput(
                    recipe.getId(),
                    recipe.getName(),
                    recipe.getServings(),
                    recipe.getPictureUrl()
            );
        }
    }

}
