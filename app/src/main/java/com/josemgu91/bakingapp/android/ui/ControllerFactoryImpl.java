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
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.View;
import com.josemgu91.bakingapp.android.Application;
import com.josemgu91.bakingapp.android.executors.DefaultThreadPoolExecutor;
import com.josemgu91.bakingapp.android.executors.UiThreadExecutor;
import com.josemgu91.bakingapp.data.remote.RemoteRetrofitRepository;

/**
 * Created by jose on 2/15/18.
 */

public class ControllerFactoryImpl implements ControllerFactory {

    private final Application application;

    private final UiThreadExecutor uiThreadExecutor;
    private final DefaultThreadPoolExecutor defaultThreadPoolExecutor;
    private final RemoteRetrofitRepository remoteRetrofitRepository;

    public ControllerFactoryImpl(Context context) {
        this.application = (Application) context.getApplicationContext();
        this.uiThreadExecutor = application.getUiThreadExecutorInstance();
        this.defaultThreadPoolExecutor = application.getDefaultThreadPoolExecutorInstance();
        this.remoteRetrofitRepository = application.getRemoteRetrofitRepositoryInstance();
    }

    public GetRecipesController createGetRecipesController(final View<GetRecipesViewModel> getRecipesView) {
        final GetRecipesPresenter getRecipesPresenter = new GetRecipesPresenter(getRecipesView, uiThreadExecutor);
        return new GetRecipesController(defaultThreadPoolExecutor, getRecipesPresenter, remoteRetrofitRepository);
    }

    @Override
    public GetRecipeIngredientsController createGetRecipeIngredientsController(View<GetRecipeIngredientsViewModel> getRecipeIngredientsViewModel) {
        final GetRecipeIngredientsPresenter getRecipeIngredientsPresenter = new GetRecipeIngredientsPresenter(getRecipeIngredientsViewModel, uiThreadExecutor);
        return new GetRecipeIngredientsController(defaultThreadPoolExecutor, getRecipeIngredientsPresenter, remoteRetrofitRepository);
    }

    @Override
    public GetRecipeStepsController createGetRecipeStepsController(View<GetRecipeStepsViewModel> getRecipeStepsViewModel) {
        final GetRecipeStepsPresenter getRecipeStepsPresenter = new GetRecipeStepsPresenter(getRecipeStepsViewModel, uiThreadExecutor);
        return new GetRecipeStepsController(defaultThreadPoolExecutor, getRecipeStepsPresenter, remoteRetrofitRepository);
    }

}
