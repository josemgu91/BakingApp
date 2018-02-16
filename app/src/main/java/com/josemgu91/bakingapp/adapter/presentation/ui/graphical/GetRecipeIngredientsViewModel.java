package com.josemgu91.bakingapp.adapter.presentation.ui.graphical;

import java.util.List;

/**
 * Created by jose on 2/16/18.
 */

public class GetRecipeIngredientsViewModel {

    private final List<Ingredient> ingredients;

    public GetRecipeIngredientsViewModel(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public String toString() {
        return "GetRecipeIngredientsViewModel{" +
                "ingredients=" + ingredients +
                '}';
    }

    public static class Ingredient {

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
        public String toString() {
            return "Ingredient{" +
                    "measureUnit='" + measureUnit + '\'' +
                    ", name='" + name + '\'' +
                    ", quantity=" + quantity +
                    '}';
        }
    }

}
