package com.josemgu91.bakingapp.domain.entities;

/**
 * Created by jose on 2/14/18.
 */

public class Ingredient {

    private final String measureUnit;
    private final String name;
    private final double quantity;

    public Ingredient(String measureUnit, String name, double quantity) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingredient that = (Ingredient) o;

        if (Double.compare(that.quantity, quantity) != 0) return false;
        if (measureUnit != null ? !measureUnit.equals(that.measureUnit) : that.measureUnit != null)
            return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = measureUnit != null ? measureUnit.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(quantity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "measureUnit='" + measureUnit + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
