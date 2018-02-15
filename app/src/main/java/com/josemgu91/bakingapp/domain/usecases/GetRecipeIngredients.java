package com.josemgu91.bakingapp.domain.usecases;

import com.josemgu91.bakingapp.domain.datagateways.DataGatewayException;
import com.josemgu91.bakingapp.domain.datagateways.IngredientDataGateway;
import com.josemgu91.bakingapp.domain.entities.Ingredient;
import com.josemgu91.bakingapp.domain.usecases.common.AbstractGetUseCase;
import com.josemgu91.bakingapp.domain.usecases.common.ListMapper;
import com.josemgu91.bakingapp.domain.usecases.common.OutputMapper;
import com.josemgu91.bakingapp.domain.usecases.common.GetUseCaseOutput;

import java.util.List;

/**
 * Created by jose on 2/14/18.
 */

public class GetRecipeIngredients extends AbstractGetUseCase<List<Ingredient>, List<IngredientOutput>> {

    private final IngredientDataGateway ingredientDataGateway;
    private final String recipeId;

    public GetRecipeIngredients(GetUseCaseOutput<List<IngredientOutput>> getUseCaseOutput, IngredientDataGateway ingredientDataGateway, String recipeId) {
        super(getUseCaseOutput, new ListMapper<>(new IngredientMapper()));
        this.ingredientDataGateway = ingredientDataGateway;
        this.recipeId = recipeId;
    }

    @Override
    protected List<Ingredient> getData() throws DataGatewayException {
        return ingredientDataGateway.getIngredients(recipeId);
    }

    private static class IngredientMapper implements OutputMapper<Ingredient, IngredientOutput> {

        @Override
        public IngredientOutput map(Ingredient ingredient) {
            return new IngredientOutput(
                    ingredient.getMeasureUnit(),
                    ingredient.getName(),
                    ingredient.getQuantity()
            );
        }
    }
}
