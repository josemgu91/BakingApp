package com.josemgu91.bakingapp.android.ui;

import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Created by jose on 2/15/18.
 */

public class UiThreadExecutor implements Executor {

    private final Handler uiThreadHandler;

    public UiThreadExecutor(Handler uiThreadHandler) {
        this.uiThreadHandler = uiThreadHandler;
    }

    @Override
    public void execute(@NonNull Runnable command) {
        uiThreadHandler.post(command);
    }
}
