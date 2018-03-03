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

package com.josemgu91.bakingapp.domain.usecases.common;

import com.josemgu91.bakingapp.domain.datagateways.DataGatewayException;
import com.josemgu91.bakingapp.domain.util.OutputMapper;

/**
 * Created by jose on 3/3/18.
 */

public abstract class AbstractSaveUseCase<Result, Output> {

    private final SaveUseCaseOutput<Output> saveUseCaseOutput;
    private final OutputMapper<Result, Output> outputMapper;

    public AbstractSaveUseCase(SaveUseCaseOutput<Output> saveUseCaseOutput, OutputMapper<Result, Output> outputMapper) {
        this.saveUseCaseOutput = saveUseCaseOutput;
        this.outputMapper = outputMapper;
    }

    public void execute() {
        try {
            saveUseCaseOutput.showInProgress();
            final Result result = saveData();
            final Output output = outputMapper.map(result);
            saveUseCaseOutput.showResult(output);
        } catch (DataGatewayException e) {
            e.printStackTrace();
            saveUseCaseOutput.showError();
        }
    }

    protected abstract Result saveData() throws DataGatewayException;

}
