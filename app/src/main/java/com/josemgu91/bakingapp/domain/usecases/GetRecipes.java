/*
 * MIT License
 *
 * Copyright (c) 2018 José Miguel García Urrutia.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.josemgu91.bakingapp.domain.usecases;

import com.josemgu91.bakingapp.domain.datagateways.DataGatewayException;
import com.josemgu91.bakingapp.domain.datagateways.FavoriteRecipesDataGateway;
import com.josemgu91.bakingapp.domain.datagateways.RecipeDataGateway;
import com.josemgu91.bakingapp.domain.entities.Recipe;
import com.josemgu91.bakingapp.domain.usecases.common.GetUseCaseOutput;
import com.josemgu91.bakingapp.domain.util.ListMapper;
import com.josemgu91.bakingapp.domain.util.OutputMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 2/14/18.
 */

public class GetRecipes {

    private final GetUseCaseOutput<List<RecipeOutput>> getUseCaseOutput;
    private final OutputMapper<List<RecipeWithFavoriteMark>, List<RecipeOutput>> outputMapper;
    private final RecipeDataGateway recipeDataGateway;
    private final FavoriteRecipesDataGateway favoriteRecipesDataGateway;
    private final int searchCriteria;

    public GetRecipes(GetUseCaseOutput<List<RecipeOutput>> getUseCaseOutput, RecipeDataGateway recipeDataGateway, FavoriteRecipesDataGateway favoriteRecipesDataGateway, Criteria criteria) {
        this.getUseCaseOutput = getUseCaseOutput;
        this.outputMapper = new ListMapper<>(new RecipeMapper());
        this.recipeDataGateway = recipeDataGateway;
        this.favoriteRecipesDataGateway = favoriteRecipesDataGateway;
        this.searchCriteria = criteria.criteria;
    }

    public void execute() {
        try {
            getUseCaseOutput.showInProgress();
            final List<RecipeWithFavoriteMark> result = new ArrayList<>();
            if (searchCriteria == Criteria.ALL_RECIPES) {
                final List<Recipe> allRecipes = recipeDataGateway.getRecipes();
                for (final Recipe recipe : allRecipes) {
                    result.add(new RecipeWithFavoriteMark(recipe, favoriteRecipesDataGateway.isFavorite(recipe.getId())));
                }
            } else if (searchCriteria == Criteria.ONLY_FAVORITE) {
                final List<Recipe> favoriteRecipes = favoriteRecipesDataGateway.getFavoriteRecipes();
                for (final Recipe recipe : favoriteRecipes) {
                    result.add(new RecipeWithFavoriteMark(recipe, true));
                }
            } else {
                //Maybe a special UseCase exception should be used?
                throw new RuntimeException("Invalid criteria during execution!");
            }
            if (result.size() == 0) {
                getUseCaseOutput.showNoResult();
            } else {
                final List<RecipeOutput> output = outputMapper.map(result);
                getUseCaseOutput.showResult(output);
            }
        } catch (DataGatewayException e) {
            e.printStackTrace();
            getUseCaseOutput.showError();
        }
    }

    private static class RecipeWithFavoriteMark {

        private final Recipe recipe;
        private final boolean isFavorite;

        public RecipeWithFavoriteMark(Recipe recipe, boolean isFavorite) {
            this.recipe = recipe;
            this.isFavorite = isFavorite;
        }
    }

    public static class Criteria {
        public static final int ALL_RECIPES = 1;
        public static final int ONLY_FAVORITE = 2;

        private int criteria;

        public Criteria(int criteria) {
            if (criteria < 1 || criteria > 2) {
                //Maybe a special UseCase exception should be used?
                throw new RuntimeException("Invalid criteria!");
            }
            this.criteria = criteria;
        }
    }

    private static class RecipeMapper implements OutputMapper<RecipeWithFavoriteMark, RecipeOutput> {

        @Override
        public RecipeOutput map(RecipeWithFavoriteMark recipeWithFavoriteMark) {
            return new RecipeOutput(
                    recipeWithFavoriteMark.recipe.getId(),
                    recipeWithFavoriteMark.recipe.getName(),
                    recipeWithFavoriteMark.recipe.getServings(),
                    recipeWithFavoriteMark.recipe.getPictureUrl(),
                    recipeWithFavoriteMark.isFavorite
            );
        }
    }

}
