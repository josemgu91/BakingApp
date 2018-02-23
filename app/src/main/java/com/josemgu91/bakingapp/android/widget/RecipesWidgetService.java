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

package com.josemgu91.bakingapp.android.widget;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.josemgu91.bakingapp.R;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.View;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.widget.GetRecipesWithIngredientsController;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.widget.GetRecipesWithIngredientsViewModel;
import com.josemgu91.bakingapp.android.ui.ControllerFactoryImpl;

/**
 * Created by jose on 2/21/18.
 */

public class RecipesWidgetService extends Service implements View<GetRecipesWithIngredientsViewModel> {

    private static final int FOREGROUND_NOTIFICATION_ID = 1;
    private static final String FOREGROUND_NOTIFICATION_CHANNEL_ID = "RecipesWidgetService";

    public final static String PARAM_WIDGET_IDS = "com.josemgu91.bakingapp.WIDGET_IDS";

    private AppWidgetManager appWidgetManager;
    private int[] widgetIds;

    @Override
    public void onCreate() {
        super.onCreate();
        final GetRecipesWithIngredientsController getRecipesWithIngredientsController = new ControllerFactoryImpl(this).createGetRecipesWithIngredientsController(this);
        getRecipesWithIngredientsController.getRecipesWithIngredients();
        appWidgetManager = AppWidgetManager.getInstance(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final NotificationChannel notificationChannel = new NotificationChannel(
                    FOREGROUND_NOTIFICATION_CHANNEL_ID,
                    getString(R.string.widget_notification_channel),
                    NotificationManager.IMPORTANCE_MIN
            );
            final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            } else {
                throw new IllegalStateException("NotificationManager is null!");
            }
            final Notification notification = new NotificationCompat.Builder(this, FOREGROUND_NOTIFICATION_CHANNEL_ID)
                    .setContentTitle(getString(R.string.widget_notification_title))
                    .setContentText(getString(R.string.widget_notification_content))
                    .setSmallIcon(R.drawable.ic_notification)
                    .build();
            startForeground(FOREGROUND_NOTIFICATION_ID, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.hasExtra(PARAM_WIDGET_IDS)) {
            widgetIds = intent.getIntArrayExtra(PARAM_WIDGET_IDS);
        } else {
            throw new IllegalStateException("The Intent hasn't the PARAM_RECIPES content!");
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void showResult(GetRecipesWithIngredientsViewModel getRecipesWithIngredientsViewModel) {
        final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_recipes);
        final Bundle recipeListBundle = new RecipeBundleMapper().toBundle(getRecipesWithIngredientsViewModel.getRecipes());
        final Intent remoteViewsServiceIntent = new Intent(this, RemoteViewsService.class)
                .putExtra(RemoteViewsService.PARAM_RECIPES, recipeListBundle);
        remoteViews.setRemoteAdapter(R.id.listview_recipes, remoteViewsServiceIntent);
        updateWidgets(remoteViews);
        finishService();
    }

    @Override
    public void showInProgress() {

    }

    @Override
    public void showRetrieveError() {
        finishService();
    }

    @Override
    public void showNoResult() {
        finishService();
    }

    private void finishService() {
        stopSelf();
    }

    private void updateWidgets(final RemoteViews remoteViews) {
        if (widgetIds == null) {
            throw new IllegalStateException("widgetIds is null!");
        }
        appWidgetManager.updateAppWidget(widgetIds, remoteViews);
    }

}
