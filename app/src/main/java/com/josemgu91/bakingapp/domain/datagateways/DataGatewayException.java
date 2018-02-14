package com.josemgu91.bakingapp.domain.datagateways;

import com.josemgu91.bakingapp.domain.DomainException;

/**
 * Created by jose on 2/14/18.
 */

public class DataGatewayException extends DomainException {

    public DataGatewayException(String message) {
        super(message);
    }
}
