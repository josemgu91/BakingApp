package com.josemgu91.bakingapp.android.ui;

import android.content.Context;

import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipesController;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipesPresenter;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipesViewModel;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.View;
import com.josemgu91.bakingapp.android.Application;
import com.josemgu91.bakingapp.data.remote.RemoteRetrofitRepository;

/**
 * Created by jose on 2/15/18.
 */

public class ControllerFactory {

    private final Application application;

    private final UiThreadExecutor uiThreadExecutor;
    private final DefaultThreadPoolExecutor defaultThreadPoolExecutor;
    private final RemoteRetrofitRepository remoteRetrofitRepository;

    public ControllerFactory(Context context) {
        this.application = (Application) context.getApplicationContext();
        this.uiThreadExecutor = application.getUiThreadExecutorInstance();
        this.defaultThreadPoolExecutor = application.getDefaultThreadPoolExecutorInstance();
        this.remoteRetrofitRepository = application.getRemoteRetrofitRepositoryInstance();
    }

    public GetRecipesController createGetRecipesController(final View<GetRecipesViewModel> getRecipesView) {
        final GetRecipesPresenter getRecipesPresenter = new GetRecipesPresenter(getRecipesView, uiThreadExecutor);
        return new GetRecipesController(getRecipesPresenter, remoteRetrofitRepository, defaultThreadPoolExecutor);
    }

}
