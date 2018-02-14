package com.josemgu91.bakingapp.domain.usecases.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 2/14/18.
 */

public class ListMapper<Source, Output> implements OutputMapper<List<Source>, List<Output>> {

    private final OutputMapper<Source, Output> outputMapper;

    public ListMapper(OutputMapper<Source, Output> outputMapper) {
        this.outputMapper = outputMapper;
    }

    @Override
    public List<Output> map(List<Source> sources) {
        final List<Output> outputCollection = new ArrayList<>();
        for (final Source source : sources) {
            outputCollection.add(outputMapper.map(source));
        }
        return outputCollection;
    }

}
