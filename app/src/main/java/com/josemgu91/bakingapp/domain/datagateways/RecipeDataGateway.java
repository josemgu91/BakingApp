package com.josemgu91.bakingapp.domain.datagateways;

import com.josemgu91.bakingapp.domain.entities.Recipe;

import java.util.List;

/**
 * Created by jose on 2/14/18.
 */

public interface RecipeDataGateway {

    List<Recipe> getRecipes() throws DataGatewayException;

}
