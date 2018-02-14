package com.josemgu91.bakingapp.domain.usecases;

/**
 * Created by jose on 2/14/18.
 */

public class IngredientOutput {

    private final String measureUnit;
    private final String name;
    private final double quantity;

    public IngredientOutput(String measureUnit, String name, double quantity) {
        this.measureUnit = measureUnit;
        this.name = name;
        this.quantity = quantity;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }
}
