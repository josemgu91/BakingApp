package com.josemgu91.bakingapp.adapter.presentation.ui.graphical;

import com.josemgu91.bakingapp.domain.usecases.RecipeOutput;
import com.josemgu91.bakingapp.domain.usecases.common.GetUseCaseOutput;
import com.josemgu91.bakingapp.domain.util.ListMapper;
import com.josemgu91.bakingapp.domain.util.OutputMapper;

import java.util.List;

/**
 * Created by jose on 2/14/18.
 */

public class GetRecipesPresenter implements GetUseCaseOutput<List<RecipeOutput>> {

    private final View<GetRecipesViewModel> view;
    private final ListMapper<RecipeOutput, GetRecipesViewModel.Recipe> recipeOutputListMapper;

    public GetRecipesPresenter(View<GetRecipesViewModel> view) {
        this.view = view;
        this.recipeOutputListMapper = new ListMapper<>(new RecipeOutputMapper());
    }

    @Override
    public void showResult(List<RecipeOutput> recipeOutputs) {
        final GetRecipesViewModel getRecipesViewModel = new GetRecipesViewModel(
                recipeOutputListMapper.map(recipeOutputs)
        );
        view.showResult(getRecipesViewModel);
    }

    @Override
    public void showInProgress() {
        view.showInProgress();
    }

    @Override
    public void showRetrieveError() {
        view.showRetrieveError();
    }

    @Override
    public void showNoResult() {
        view.showNoResult();
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
