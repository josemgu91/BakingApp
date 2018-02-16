package com.josemgu91.bakingapp.adapter.presentation.ui.graphical;

import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.common.AbstractGetPresenter;
import com.josemgu91.bakingapp.domain.usecases.RecipeOutput;
import com.josemgu91.bakingapp.domain.util.ListMapper;
import com.josemgu91.bakingapp.domain.util.OutputMapper;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by jose on 2/14/18.
 */

public class GetRecipesPresenter extends AbstractGetPresenter<List<RecipeOutput>, GetRecipesViewModel> {

    private final ListMapper<RecipeOutput, GetRecipesViewModel.Recipe> recipeOutputListMapper;

    public GetRecipesPresenter(View<GetRecipesViewModel> view, Executor viewExecutor) {
        super(view, viewExecutor);
        this.recipeOutputListMapper = new ListMapper<>(new RecipeOutputMapper());
    }

    @Override
    protected GetRecipesViewModel createResultViewModel(List<RecipeOutput> recipeOutputs) {
        return new GetRecipesViewModel(
                recipeOutputListMapper.map(recipeOutputs)
        );
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
