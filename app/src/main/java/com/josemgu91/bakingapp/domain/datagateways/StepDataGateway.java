package com.josemgu91.bakingapp.domain.datagateways;

import com.josemgu91.bakingapp.domain.entities.Step;

/**
 * Created by jose on 2/14/18.
 */

public interface StepDataGateway {

    Step getSteps(final String recipeId) throws DataGatewayException;

}
