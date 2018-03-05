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
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.josemgu91.bakingapp.R;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetView;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.widget.GetFavoriteRecipesWithIngredientsController;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.widget.GetFavoriteRecipesWithIngredientsViewModel;
import com.josemgu91.bakingapp.android.ui.ControllerFactoryImpl;
import com.josemgu91.bakingapp.android.ui.RecipeDetailActivity;

import java.util.Arrays;

/**
 * Created by jose on 2/21/18.
 */

public class RecipesWidgetService extends Service implements GetView<GetFavoriteRecipesWithIngredientsViewModel> {

    private static final int FOREGROUND_NOTIFICATION_ID = 1;
    private static final String FOREGROUND_NOTIFICATION_CHANNEL_ID = "RecipesWidgetService";

    public final static String PARAM_WIDGET_IDS = "com.josemgu91.bakingapp.WIDGET_IDS";

    private AppWidgetManager appWidgetManager;
    private int[] widgetIds;

    @Override
    public void onCreate() {
        super.onCreate();
        final GetFavoriteRecipesWithIngredientsController getFavoriteRecipesWithIngredientsController = new ControllerFactoryImpl(this).createGetRecipesWithIngredientsController(this);
        getFavoriteRecipesWithIngredientsController.getFavoriteRecipesWithIngredients();
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
            Log.d("RecipesWidgetService", "widgetIds: " + Arrays.toString(widgetIds));
        } else {
            throw new IllegalStateException("The Intent hasn't the PARAM_RECIPES content!");
        }
        return START_NOT_STICKY;
    }

    public static void update(final Context context, final int[] appWidgetIds) {
        final Intent intent = new Intent(context, RecipesWidgetService.class);
        intent.putExtra(RecipesWidgetService.PARAM_WIDGET_IDS, appWidgetIds);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /*Starting the service as foreground because the Android O background execution limits.
             *See: https://developer.android.com/about/versions/oreo/background.html
             */
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void showResult(GetFavoriteRecipesWithIngredientsViewModel getFavoriteRecipesWithIngredientsViewModel) {
        updateResult();
        finishService();
    }

    @Override
    public void showInProgress() {

    }

    @Override
    public void showError() {
        finishService();
    }

    @Override
    public void showNoResult() {
        updateResult();
        finishService();
    }

    private void updateResult() {
        final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_recipes);
        final Intent remoteViewsServiceIntent = new Intent(this, RemoteViewsService.class);
        remoteViews.setRemoteAdapter(R.id.listview_recipes, remoteViewsServiceIntent);
        /*
         * TODO: I'm not so sure why this restarts the "RecipeDetailActivity"
         * (whose launch mode is "singleTask"), instead of calling its "onNewIntent" method.
         */
        remoteViews.setPendingIntentTemplate(
                R.id.listview_recipes,
                PendingIntent.getActivities(
                        this,
                        1,
                        TaskStackBuilder
                                .create(this)
                                .addParentStack(RecipeDetailActivity.class)
                                .addNextIntent(new Intent(this, RecipeDetailActivity.class))
                                .getIntents(),
                        PendingIntent.FLAG_UPDATE_CURRENT
                )
        );
        updateWidgets(remoteViews);
    }

    private void finishService() {
        stopSelf();
    }

    private void updateWidgets(final RemoteViews remoteViews) {
        if (widgetIds == null) {
            throw new IllegalStateException("widgetIds is null!");
        }
        appWidgetManager.notifyAppWidgetViewDataChanged(widgetIds, R.id.listview_recipes);
        appWidgetManager.updateAppWidget(widgetIds, remoteViews);
    }

}
