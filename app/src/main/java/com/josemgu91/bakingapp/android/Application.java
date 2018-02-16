package com.josemgu91.bakingapp.android;

import com.josemgu91.bakingapp.android.executors.DefaultThreadPoolExecutor;
import com.josemgu91.bakingapp.android.executors.UiThreadExecutor;
import com.josemgu91.bakingapp.data.remote.RemoteRetrofitRepository;

/**
 * Created by jose on 2/15/18.
 */

public class Application extends android.app.Application {

    private UiThreadExecutor uiThreadExecutor;
    private DefaultThreadPoolExecutor defaultThreadPoolExecutor;
    private RemoteRetrofitRepository remoteRetrofitRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        uiThreadExecutor = new UiThreadExecutor();
        defaultThreadPoolExecutor = new DefaultThreadPoolExecutor();
        remoteRetrofitRepository = new RemoteRetrofitRepository();
    }

    public UiThreadExecutor getUiThreadExecutorInstance() {
        return uiThreadExecutor;
    }

    public DefaultThreadPoolExecutor getDefaultThreadPoolExecutorInstance() {
        return defaultThreadPoolExecutor;
    }

    public RemoteRetrofitRepository getRemoteRetrofitRepositoryInstance() {
        return remoteRetrofitRepository;
    }

}
