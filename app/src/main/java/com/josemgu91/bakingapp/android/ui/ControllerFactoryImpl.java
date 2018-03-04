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

package com.josemgu91.bakingapp.android.ui;

import android.content.Context;

import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeIngredientsController;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeIngredientsPresenter;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeIngredientsViewModel;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeStepsController;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeStepsPresenter;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeStepsViewModel;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipesController;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipesPresenter;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipesViewModel;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetView;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.MarkRecipeAsFavoriteController;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.MarkRecipeAsFavoritePresenter;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.MarkRecipeAsFavoriteViewModel;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.View;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.widget.GetRecipesWithIngredientsController;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.widget.GetRecipesWithIngredientsPresenter;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.widget.GetRecipesWithIngredientsViewModel;
import com.josemgu91.bakingapp.android.Application;
import com.josemgu91.bakingapp.android.executors.DefaultThreadPoolExecutor;
import com.josemgu91.bakingapp.android.executors.UiThreadExecutor;
import com.josemgu91.bakingapp.data.local.FavoriteRepository;
import com.josemgu91.bakingapp.data.remote.RemoteRetrofitRepository;

/**
 * Created by jose on 2/15/18.
 */

public class ControllerFactoryImpl implements ControllerFactory {

    private final UiThreadExecutor uiThreadExecutor;
    private final DefaultThreadPoolExecutor defaultThreadPoolExecutor;
    private final RemoteRetrofitRepository remoteRetrofitRepository;
    private final FavoriteRepository favoriteRepository;

    public ControllerFactoryImpl(Context context) {
        final Application application = (Application) context.getApplicationContext();
        this.uiThreadExecutor = application.getUiThreadExecutorInstance();
        this.defaultThreadPoolExecutor = application.getDefaultThreadPoolExecutorInstance();
        this.remoteRetrofitRepository = application.getRemoteRetrofitRepositoryInstance();
        this.favoriteRepository = application.getFavoriteRepositoryInstance();
    }

    @Override
    public GetRecipesController createGetRecipesController(final GetView<GetRecipesViewModel> getRecipesView) {
        final GetRecipesPresenter getRecipesPresenter = new GetRecipesPresenter(getRecipesView, uiThreadExecutor);
        return new GetRecipesController(defaultThreadPoolExecutor, getRecipesPresenter, remoteRetrofitRepository, favoriteRepository);
    }

    @Override
    public GetRecipeIngredientsController createGetRecipeIngredientsController(GetView<GetRecipeIngredientsViewModel> getRecipeIngredientsViewModel) {
        final GetRecipeIngredientsPresenter getRecipeIngredientsPresenter = new GetRecipeIngredientsPresenter(getRecipeIngredientsViewModel, uiThreadExecutor);
        return new GetRecipeIngredientsController(defaultThreadPoolExecutor, getRecipeIngredientsPresenter, remoteRetrofitRepository);
    }

    @Override
    public GetRecipeStepsController createGetRecipeStepsController(GetView<GetRecipeStepsViewModel> getRecipeStepsViewModel) {
        final GetRecipeStepsPresenter getRecipeStepsPresenter = new GetRecipeStepsPresenter(getRecipeStepsViewModel, uiThreadExecutor);
        return new GetRecipeStepsController(defaultThreadPoolExecutor, getRecipeStepsPresenter, remoteRetrofitRepository);
    }

    @Override
    public GetRecipesWithIngredientsController createGetRecipesWithIngredientsController(GetView<GetRecipesWithIngredientsViewModel> getRecipesWithIngredientsViewModel) {
        final GetRecipesWithIngredientsPresenter getRecipesWithIngredientsPresenter = new GetRecipesWithIngredientsPresenter(getRecipesWithIngredientsViewModel, uiThreadExecutor);
        return new GetRecipesWithIngredientsController(defaultThreadPoolExecutor, getRecipesWithIngredientsPresenter, remoteRetrofitRepository);
    }

    @Override
    public MarkRecipeAsFavoriteController createMarkRecipeAsFavoriteController(View<MarkRecipeAsFavoriteViewModel> markRecipeAsFavoriteViewModel) {
        final MarkRecipeAsFavoritePresenter markRecipeAsFavoritePresenter = new MarkRecipeAsFavoritePresenter(markRecipeAsFavoriteViewModel, uiThreadExecutor);
        return new MarkRecipeAsFavoriteController(defaultThreadPoolExecutor, markRecipeAsFavoritePresenter, favoriteRepository);
    }
}
