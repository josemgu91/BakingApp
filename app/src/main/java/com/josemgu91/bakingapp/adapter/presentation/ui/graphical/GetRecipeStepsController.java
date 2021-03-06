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

package com.josemgu91.bakingapp.adapter.presentation.ui.graphical;

import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.common.AbstractController;
import com.josemgu91.bakingapp.domain.datagateways.StepDataGateway;
import com.josemgu91.bakingapp.domain.usecases.GetRecipeSteps;
import com.josemgu91.bakingapp.domain.usecases.StepOutput;
import com.josemgu91.bakingapp.domain.usecases.common.GetUseCaseOutput;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by jose on 2/16/18.
 */

public class GetRecipeStepsController extends AbstractController {

    private final GetUseCaseOutput<List<StepOutput>> getRecipeStepsUseCaseOutput;
    private final StepDataGateway stepDataGateway;

    public GetRecipeStepsController(Executor controllerExecutor, GetUseCaseOutput<List<StepOutput>> getRecipeStepsUseCaseOutput, StepDataGateway stepDataGateway) {
        super(controllerExecutor);
        this.getRecipeStepsUseCaseOutput = getRecipeStepsUseCaseOutput;
        this.stepDataGateway = stepDataGateway;
    }

    public void getSteps(final String recipeId) {
        executeInControllerExecutor(new Runnable() {
            @Override
            public void run() {
                new GetRecipeSteps(getRecipeStepsUseCaseOutput, stepDataGateway, recipeId).execute();
            }
        });
    }
}
