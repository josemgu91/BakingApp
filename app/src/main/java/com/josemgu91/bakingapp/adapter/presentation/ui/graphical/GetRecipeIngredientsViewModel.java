/*
 * MIT License
 *
 * Copyright (c) 2018 José Miguel García Urrutia.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GetRecipeIngredientsViewModel that = (GetRecipeIngredientsViewModel) o;

        return ingredients != null ? ingredients.equals(that.ingredients) : that.ingredients == null;
    }

    @Override
    public int hashCode() {
        return ingredients != null ? ingredients.hashCode() : 0;
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
    }

}
