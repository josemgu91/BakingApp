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
import com.josemgu91.bakingapp.domain.usecases.common.AbstractSaveUseCase;
import com.josemgu91.bakingapp.domain.usecases.common.SaveUseCaseOutput;
import com.josemgu91.bakingapp.domain.util.OutputMapper;

/**
 * Created by jose on 3/3/18.
 */

public class SaveFavoriteRecipe extends AbstractSaveUseCase<Boolean, SaveFavoriteRecipeOutput> {

    private final FavoriteRecipesDataGateway favoriteRecipesDataGateway;
    private final String recipeId;

    public SaveFavoriteRecipe(final SaveUseCaseOutput<SaveFavoriteRecipeOutput> saveUseCaseOutput, final FavoriteRecipesDataGateway favoriteRecipesDataGateway, final String recipeId) {
        super(saveUseCaseOutput, new SaveFavoriteRecipeMapper(recipeId));
        this.favoriteRecipesDataGateway = favoriteRecipesDataGateway;
        this.recipeId = recipeId;
    }

    @Override
    protected Boolean saveData() throws DataGatewayException {
        return favoriteRecipesDataGateway.saveFavoriteRecipe(recipeId);
    }

    private static class SaveFavoriteRecipeMapper implements OutputMapper<Boolean, SaveFavoriteRecipeOutput> {

        private final String recipeId;

        public SaveFavoriteRecipeMapper(String recipeId) {
            this.recipeId = recipeId;
        }

        @Override
        public SaveFavoriteRecipeOutput map(Boolean aBoolean) {
            return new SaveFavoriteRecipeOutput(
                    aBoolean,
                    recipeId
            );
        }
    }
}
