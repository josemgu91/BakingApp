package com.josemgu91.bakingapp.adapter.presentation.ui.graphical.common;

import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.View;
import com.josemgu91.bakingapp.domain.usecases.common.GetUseCaseOutput;

import java.util.concurrent.Executor;

/**
 * Created by jose on 2/16/18.
 */

public abstract class AbstractGetPresenter<UseCaseOutput, ViewModel> implements GetUseCaseOutput<UseCaseOutput> {

    private final View<ViewModel> view;
    private final Executor viewExecutor;

    public AbstractGetPresenter(View<ViewModel> view, Executor viewExecutor) {
        this.view = view;
        this.viewExecutor = viewExecutor;
    }

    protected abstract ViewModel createResultViewModel(UseCaseOutput useCaseOutput);

    @Override
    public void showResult(UseCaseOutput useCaseOutput) {
        final ViewModel resultViewModel = createResultViewModel(useCaseOutput);
        viewExecutor.execute(new Runnable() {
            @Override
            public void run() {
                view.showResult(resultViewModel);
            }
        });
    }

    @Override
    public void showInProgress() {
        viewExecutor.execute(new Runnable() {
            @Override
            public void run() {
                view.showInProgress();
            }
        });
    }

    @Override
    public void showRetrieveError() {
        viewExecutor.execute(new Runnable() {
            @Override
            public void run() {
                view.showRetrieveError();
            }
        });
    }

    @Override
    public void showNoResult() {
        viewExecutor.execute(new Runnable() {
            @Override
            public void run() {
                view.showNoResult();
            }
        });
    }
}
