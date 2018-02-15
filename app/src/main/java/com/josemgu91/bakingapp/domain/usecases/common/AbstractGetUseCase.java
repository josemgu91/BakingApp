package com.josemgu91.bakingapp.domain.usecases.common;

import com.josemgu91.bakingapp.domain.datagateways.DataGatewayException;
import com.josemgu91.bakingapp.domain.util.OutputMapper;

import java.util.Collection;

/**
 * Created by jose on 2/14/18.
 */

public abstract class AbstractGetUseCase<Entity extends Collection, Output extends Collection> {

    private final GetUseCaseOutput<Output> getUseCaseOutput;
    private final OutputMapper<Entity, Output> outputMapper;

    public AbstractGetUseCase(GetUseCaseOutput<Output> getUseCaseOutput, OutputMapper<Entity, Output> outputMapper) {
        this.getUseCaseOutput = getUseCaseOutput;
        this.outputMapper = outputMapper;
    }

    public void execute() {
        try {
            getUseCaseOutput.showInProgress();
            final Entity data = getData();
            if (data.size() == 0) {
                getUseCaseOutput.showNoResult();
            } else {
                final Output output = outputMapper.map(data);
                getUseCaseOutput.showResult(output);
            }
        } catch (DataGatewayException e) {
            e.printStackTrace();
            getUseCaseOutput.showRetrieveError();
        }
    }

    protected abstract Entity getData() throws DataGatewayException;

}
