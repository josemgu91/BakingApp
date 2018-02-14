package com.josemgu91.bakingapp.domain.usecases.common;

/**
 * Created by jose on 2/14/18.
 */

public interface OutputMapper<Source, Output> {

    Output map(Source source);

}
