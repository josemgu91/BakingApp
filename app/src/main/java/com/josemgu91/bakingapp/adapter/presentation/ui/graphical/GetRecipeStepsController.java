package com.josemgu91.bakingapp.adapter.presentation.ui.graphical;

import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.common.AbstractController;
import com.josemgu91.bakingapp.domain.datagateways.StepDataGateway;
import com.josemgu91.bakingapp.domain.usecases.GetRecipeSteps;
import com.josemgu91.bakingapp.domain.usecases.StepOutput;
import com.josemgu91.bakingapp.domain.usecases.common.GetUseCaseOutput;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by jose on 2/16/18.
 */

public class GetRecipeStepsController extends AbstractController {

    private final GetUseCaseOutput<List<StepOutput>> getRecipeStepsUseCaseOutput;
    private final StepDataGateway stepDataGateway;

    public GetRecipeStepsController(Executor controllerExecutor, GetUseCaseOutput<List<StepOutput>> getRecipeStepsUseCaseOutput, StepDataGateway stepDataGateway) {
        super(controllerExecutor);
        this.getRecipeStepsUseCaseOutput = getRecipeStepsUseCaseOutput;
        this.stepDataGateway = stepDataGateway;
    }

    public void getSteps(final String recipeId) {
        executeInControllerExecutor(new Runnable() {
            @Override
            public void run() {
                new GetRecipeSteps(getRecipeStepsUseCaseOutput, stepDataGateway, recipeId).execute();
            }
        });
    }
}
