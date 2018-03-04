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
import com.josemgu91.bakingapp.domain.usecases.common.UseCaseOutput;
import com.josemgu91.bakingapp.domain.util.OutputMapper;

/**
 * Created by jose on 3/3/18.
 */

public class MarkRecipeAsFavorite {

    private final UseCaseOutput<MarkRecipeAsFavoriteOutput> useCaseOutput;
    private final OutputMapper<Boolean, MarkRecipeAsFavoriteOutput> outputMapper;
    private final FavoriteRecipesDataGateway favoriteRecipesDataGateway;
    private final Input input;

    public MarkRecipeAsFavorite(final UseCaseOutput<MarkRecipeAsFavoriteOutput> useCaseOutput, final FavoriteRecipesDataGateway favoriteRecipesDataGateway, final Input input) {
        this.useCaseOutput = useCaseOutput;
        this.outputMapper = new SaveFavoriteRecipeMapper(input.recipeId);
        this.favoriteRecipesDataGateway = favoriteRecipesDataGateway;
        this.input = input;
    }

    public void execute() {
        try {
            useCaseOutput.showInProgress();
            final boolean isAlreadyFavorite = favoriteRecipesDataGateway.isFavorite(input.recipeId);
            final boolean result;
            if (input.markAsFavorite) {
                result = isAlreadyFavorite || favoriteRecipesDataGateway.saveFavoriteRecipe(input.recipeId);
            } else {
                result = !isAlreadyFavorite || favoriteRecipesDataGateway.deleteFavoriteRecipe(input.recipeId);
            }
            final MarkRecipeAsFavoriteOutput markRecipeAsFavoriteOutput = outputMapper.map(result);
            useCaseOutput.showResult(markRecipeAsFavoriteOutput);
        } catch (DataGatewayException e) {
            e.printStackTrace();
            useCaseOutput.showError();
        }
    }

    public static class Input {
        private final String recipeId;
        private final boolean markAsFavorite;

        public Input(String recipeId, boolean markAsFavorite) {
            this.recipeId = recipeId;
            this.markAsFavorite = markAsFavorite;
        }
    }

    private static class SaveFavoriteRecipeMapper implements OutputMapper<Boolean, MarkRecipeAsFavoriteOutput> {

        private final String recipeId;

        public SaveFavoriteRecipeMapper(String recipeId) {
            this.recipeId = recipeId;
        }

        @Override
        public MarkRecipeAsFavoriteOutput map(Boolean aBoolean) {
            return new MarkRecipeAsFavoriteOutput(
                    aBoolean,
                    recipeId
            );
        }
    }
}
