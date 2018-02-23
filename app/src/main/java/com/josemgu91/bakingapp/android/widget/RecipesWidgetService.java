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

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.View;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.widget.GetRecipesWithIngredientsController;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.widget.GetRecipesWithIngredientsViewModel;
import com.josemgu91.bakingapp.android.ui.ControllerFactoryImpl;

/**
 * Created by jose on 2/21/18.
 */

public class RecipesWidgetService extends Service implements View<GetRecipesWithIngredientsViewModel> {

    @Override
    public void onCreate() {
        super.onCreate();
        final GetRecipesWithIngredientsController getRecipesWithIngredientsController = new ControllerFactoryImpl(this).createGetRecipesWithIngredientsController(this);
        getRecipesWithIngredientsController.getRecipesWithIngredients();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void showResult(GetRecipesWithIngredientsViewModel getRecipesWithIngredientsViewModel) {

    }

    @Override
    public void showInProgress() {

    }

    @Override
    public void showRetrieveError() {

    }

    @Override
    public void showNoResult() {

    }
}
