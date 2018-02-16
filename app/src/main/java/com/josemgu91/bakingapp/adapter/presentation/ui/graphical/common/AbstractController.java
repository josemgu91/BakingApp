package com.josemgu91.bakingapp.adapter.presentation.ui.graphical.common;

import java.util.concurrent.Executor;

/**
 * Created by jose on 2/16/18.
 */

public abstract class AbstractController {

    private final Executor controllerExecutor;

    public AbstractController(Executor controllerExecutor) {
        this.controllerExecutor = controllerExecutor;
    }

    protected void executeInControllerExecutor(final Runnable runnable) {
        controllerExecutor.execute(runnable);
    }

}
