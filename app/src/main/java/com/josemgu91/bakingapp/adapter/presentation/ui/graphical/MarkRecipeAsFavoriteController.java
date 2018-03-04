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

package com.josemgu91.bakingapp.adapter.presentation.ui.graphical;

import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.common.AbstractController;
import com.josemgu91.bakingapp.domain.datagateways.FavoriteRecipesDataGateway;
import com.josemgu91.bakingapp.domain.usecases.MarkRecipeAsFavorite;
import com.josemgu91.bakingapp.domain.usecases.MarkRecipeAsFavoriteOutput;
import com.josemgu91.bakingapp.domain.usecases.common.UseCaseOutput;

import java.util.concurrent.Executor;

/**
 * Created by jose on 3/4/18.
 */

public class MarkRecipeAsFavoriteController extends AbstractController {

    private final UseCaseOutput<MarkRecipeAsFavoriteOutput> markRecipeAsFavoriteUseCaseOutput;
    private final FavoriteRecipesDataGateway favoriteRecipesDataGateway;

    public MarkRecipeAsFavoriteController(Executor controllerExecutor, UseCaseOutput<MarkRecipeAsFavoriteOutput> markRecipeAsFavoriteUseCaseOutput, FavoriteRecipesDataGateway favoriteRecipesDataGateway) {
        super(controllerExecutor);
        this.markRecipeAsFavoriteUseCaseOutput = markRecipeAsFavoriteUseCaseOutput;
        this.favoriteRecipesDataGateway = favoriteRecipesDataGateway;
    }

    public void markAsFavorite(final String recipeId) {
        executeInControllerExecutor(new Runnable() {
            @Override
            public void run() {
                new MarkRecipeAsFavorite(markRecipeAsFavoriteUseCaseOutput, favoriteRecipesDataGateway, new MarkRecipeAsFavorite.Input(recipeId, true)).execute();
            }
        });
    }

    public void unmarkAsFavorite(final String recipeId) {
        executeInControllerExecutor(new Runnable() {
            @Override
            public void run() {
                new MarkRecipeAsFavorite(markRecipeAsFavoriteUseCaseOutput, favoriteRecipesDataGateway, new MarkRecipeAsFavorite.Input(recipeId, false)).execute();
            }
        });
    }
}
