package com.josemgu91.bakingapp.android.ui;

import android.support.annotation.NonNull;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by jose on 2/15/18.
 */

public class DefaultThreadPoolExecutor implements Executor {

    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = 1;
    private static final int MAX_POOL_SIZE = NUMBER_OF_CORES;
    private static final int KEEP_ALIVE_TIME = 120;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    private final AbstractExecutorService threadPoolExecutor;

    public DefaultThreadPoolExecutor() {
        final BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(MAX_POOL_SIZE);
        threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                workQueue
        );
    }

    @Override
    public void execute(@NonNull Runnable command) {
        threadPoolExecutor.execute(command);
    }
}
