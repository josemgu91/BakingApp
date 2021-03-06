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

package com.josemgu91.bakingapp.adapter.presentation.ui.graphical.common;

import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.View;
import com.josemgu91.bakingapp.domain.usecases.common.UseCaseOutput;

import java.util.concurrent.Executor;

/**
 * Created by jose on 3/4/18.
 */

public abstract class AbstractPresenter<UseCaseOutputType, ViewModel> implements UseCaseOutput<UseCaseOutputType> {

    final View<ViewModel> view;
    final Executor viewExecutor;

    public AbstractPresenter(View<ViewModel> view, Executor viewExecutor) {
        this.view = view;
        this.viewExecutor = viewExecutor;
    }

    protected abstract ViewModel createResultViewModel(UseCaseOutputType useCaseOutput);

    @Override
    public void showResult(UseCaseOutputType useCaseOutput) {
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
    public void showError() {
        viewExecutor.execute(new Runnable() {
            @Override
            public void run() {
                view.showError();
            }
        });
    }

}
