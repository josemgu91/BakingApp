package com.josemgu91.bakingapp.domain.datagateways;

import com.josemgu91.bakingapp.domain.entities.Ingredient;

/**
 * Created by jose on 2/14/18.
 */

public interface IngredientDataGateway {

    Ingredient getIngredients(final String recipeId) throws DataGatewayException;

}
