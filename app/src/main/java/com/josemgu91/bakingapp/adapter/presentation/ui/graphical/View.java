package com.josemgu91.bakingapp.adapter.presentation.ui.graphical;

/**
 * Created by jose on 2/15/18.
 */

public interface View<ViewModel> {

    void showResult(final ViewModel viewModel);

    void showInProgress();

    void showRetrieveError();

    void showNoResult();

}
