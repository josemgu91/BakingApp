package com.josemgu91.bakingapp.domain.usecases.common;

import com.josemgu91.bakingapp.domain.datagateways.DataGatewayException;

import java.util.Collection;

/**
 * Created by jose on 2/14/18.
 */

public abstract class AbstractGetUseCase<Entity extends Collection, Output extends Collection> {

    private final UseCaseOutput<Output> useCaseOutput;
    private final OutputMapper<Entity, Output> outputMapper;

    public AbstractGetUseCase(UseCaseOutput<Output> useCaseOutput, OutputMapper<Entity, Output> outputMapper) {
        this.useCaseOutput = useCaseOutput;
        this.outputMapper = outputMapper;
    }

    public void execute() {
        try {
            useCaseOutput.showInProgress();
            final Entity data = getData();
            if (data.size() == 0) {
                useCaseOutput.showNoResult();
            } else {
                final Output output = outputMapper.map(data);
                useCaseOutput.showResult(output);
            }
        } catch (DataGatewayException e) {
            e.printStackTrace();
            useCaseOutput.showRetrieveError();
        }
    }

    protected abstract Entity getData() throws DataGatewayException;

}
