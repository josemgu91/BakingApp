package com.josemgu91.bakingapp.domain.datagateways;

import com.josemgu91.bakingapp.domain.entities.Step;

import java.util.List;

/**
 * Created by jose on 2/14/18.
 */

public interface StepDataGateway {

    List<Step> getSteps(final String recipeId) throws DataGatewayException;

}
