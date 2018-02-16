package com.josemgu91.bakingapp.adapter.presentation.ui.graphical;

import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.common.AbstractGetPresenter;
import com.josemgu91.bakingapp.domain.usecases.StepOutput;
import com.josemgu91.bakingapp.domain.util.ListMapper;
import com.josemgu91.bakingapp.domain.util.OutputMapper;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by jose on 2/16/18.
 */

public class GetRecipeStepsPresenter extends AbstractGetPresenter<List<StepOutput>, GetRecipeStepsViewModel> {

    private final ListMapper<StepOutput, GetRecipeStepsViewModel.Step> stepsOutputListMapper;

    public GetRecipeStepsPresenter(View<GetRecipeStepsViewModel> view, Executor viewExecutor) {
        super(view, viewExecutor);
        this.stepsOutputListMapper = new ListMapper<>(new StepOutputMapper());
    }

    @Override
    protected GetRecipeStepsViewModel createResultViewModel(List<StepOutput> stepOutputs) {
        return new GetRecipeStepsViewModel(
                stepsOutputListMapper.map(stepOutputs)
        );
    }

    private static class StepOutputMapper implements OutputMapper<StepOutput, GetRecipeStepsViewModel.Step> {

        @Override
        public GetRecipeStepsViewModel.Step map(StepOutput stepOutput) {
            return new GetRecipeStepsViewModel.Step(
                    stepOutput.getShortDescription(),
                    stepOutput.getLongDescription(),
                    stepOutput.getPictureUrl(),
                    stepOutput.getVideoUrl()
            );
        }
    }
}
