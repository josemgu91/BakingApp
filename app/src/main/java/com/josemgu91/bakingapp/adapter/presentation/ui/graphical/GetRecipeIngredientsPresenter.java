package com.josemgu91.bakingapp.adapter.presentation.ui.graphical;

import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.common.AbstractGetPresenter;
import com.josemgu91.bakingapp.domain.usecases.IngredientOutput;
import com.josemgu91.bakingapp.domain.util.ListMapper;
import com.josemgu91.bakingapp.domain.util.OutputMapper;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by jose on 2/16/18.
 */

public class GetRecipeIngredientsPresenter extends AbstractGetPresenter<List<IngredientOutput>, GetRecipeIngredientsViewModel> {

    private final ListMapper<IngredientOutput, GetRecipeIngredientsViewModel.Ingredient> ingredientOutputListMapper;

    public GetRecipeIngredientsPresenter(View<GetRecipeIngredientsViewModel> view, Executor viewExecutor) {
        super(view, viewExecutor);
        this.ingredientOutputListMapper = new ListMapper<>(new IngredientOutputMapper());
    }

    @Override
    protected GetRecipeIngredientsViewModel createResultViewModel(List<IngredientOutput> ingredientOutput) {
        return new GetRecipeIngredientsViewModel(
                ingredientOutputListMapper.map(ingredientOutput)
        );
    }

    private class IngredientOutputMapper implements OutputMapper<IngredientOutput, GetRecipeIngredientsViewModel.Ingredient> {

        @Override
        public GetRecipeIngredientsViewModel.Ingredient map(IngredientOutput ingredientOutput) {
            return new GetRecipeIngredientsViewModel.Ingredient(
                    ingredientOutput.getMeasureUnit(),
                    ingredientOutput.getName(),
                    ingredientOutput.getQuantity()
            );
        }
    }

}
