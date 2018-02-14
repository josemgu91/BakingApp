package com.josemgu91.bakingapp.domain.datagateways;

import com.josemgu91.bakingapp.domain.entities.Recipe;

/**
 * Created by jose on 2/14/18.
 */

public interface RecipeDataGateway {

    Recipe getRecipies() throws DataGatewayException;

}
