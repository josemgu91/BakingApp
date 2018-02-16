package com.josemgu91.bakingapp.adapter.presentation.ui.graphical;

import com.josemgu91.bakingapp.domain.usecases.RecipeOutput;
import com.josemgu91.bakingapp.domain.usecases.common.GetUseCaseOutput;
import com.josemgu91.bakingapp.domain.util.ListMapper;
import com.josemgu91.bakingapp.domain.util.OutputMapper;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by jose on 2/14/18.
 */

public class GetRecipesPresenter implements GetUseCaseOutput<List<RecipeOutput>> {

    private final View<GetRecipesViewModel> view;
    private final Executor viewExecutor;
    private final ListMapper<RecipeOutput, GetRecipesViewModel.Recipe> recipeOutputListMapper;

    public GetRecipesPresenter(View<GetRecipesViewModel> view, Executor viewExecutor) {
        this.view = view;
        this.viewExecutor = viewExecutor;
        this.recipeOutputListMapper = new ListMapper<>(new RecipeOutputMapper());
    }

    @Override
    public void showResult(List<RecipeOutput> recipeOutputs) {
        final GetRecipesViewModel getRecipesViewModel = new GetRecipesViewModel(
                recipeOutputListMapper.map(recipeOutputs)
        );
        viewExecutor.execute(new Runnable() {
            @Override
            public void run() {
                view.showResult(getRecipesViewModel);
            }
        });
    }

    @Override
    public void showInProgress() {
        viewExecutor.execute(new Runnable() {
            @Override
            public void run() {
                view.showInProgress();
            }
        });
    }

    @Override
    public void showRetrieveError() {
        viewExecutor.execute(new Runnable() {
            @Override
            public void run() {
                view.showRetrieveError();
            }
        });
    }

    @Override
    public void showNoResult() {
        viewExecutor.execute(new Runnable() {
            @Override
            public void run() {
                view.showNoResult();
            }
        });
    }

    private class RecipeOutputMapper implements OutputMapper<RecipeOutput, GetRecipesViewModel.Recipe> {

        @Override
        public GetRecipesViewModel.Recipe map(RecipeOutput recipeOutput) {
            return new GetRecipesViewModel.Recipe(
                    recipeOutput.getId(),
                    recipeOutput.getName(),
                    recipeOutput.getServings(),
                    recipeOutput.getPictureUrl()
            );
        }
    }

}
