package com.josemgu91.bakingapp.domain.usecases.common;

/**
 * Created by jose on 2/14/18.
 */

public interface UseCaseOutput<Output> {

    void showResult(final Output output);

    void showInProgress();

    void showRetrieveError();

    void showNoResult();

}
