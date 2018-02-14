package com.josemgu91.bakingapp.domain.usecases;

import com.josemgu91.bakingapp.domain.datagateways.DataGatewayException;
import com.josemgu91.bakingapp.domain.datagateways.StepDataGateway;
import com.josemgu91.bakingapp.domain.entities.Step;
import com.josemgu91.bakingapp.domain.usecases.common.AbstractGetUseCase;
import com.josemgu91.bakingapp.domain.usecases.common.ListMapper;
import com.josemgu91.bakingapp.domain.usecases.common.OutputMapper;
import com.josemgu91.bakingapp.domain.usecases.common.UseCaseOutput;

import java.util.List;

/**
 * Created by jose on 2/14/18.
 */

public class GetRecipeSteps extends AbstractGetUseCase<List<Step>, List<StepOutput>> {

    private final StepDataGateway stepDataGateway;
    private final String recipeId;

    public GetRecipeSteps(UseCaseOutput<List<StepOutput>> useCaseOutput, StepDataGateway stepDataGateway, String recipeId) {
        super(useCaseOutput, new ListMapper<>(new StepMapper()));
        this.stepDataGateway = stepDataGateway;
        this.recipeId = recipeId;
    }

    @Override
    protected List<Step> getData() throws DataGatewayException {
        return stepDataGateway.getSteps(recipeId);
    }

    private static class StepMapper implements OutputMapper<Step, StepOutput> {

        @Override
        public StepOutput map(Step step) {
            return new StepOutput(
                    step.getShortDescription(),
                    step.getLongDescription(),
                    step.getPictureUrl(),
                    step.getVideoUrl()
            );
        }
    }
}
