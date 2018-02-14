package com.josemgu91.bakingapp.domain.datagateways;

import com.josemgu91.bakingapp.domain.entities.Ingredient;

import java.util.List;

/**
 * Created by jose on 2/14/18.
 */

public interface IngredientDataGateway {

    List<Ingredient> getIngredients(final String recipeId) throws DataGatewayException;

}
