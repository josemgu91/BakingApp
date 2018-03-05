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

package com.josemgu91.bakingapp.adapter.presentation.ui.graphical.widget;

import java.util.List;

/**
 * Created by jose on 2/22/18.
 */

public class GetFavoriteRecipesWithIngredientsViewModel {

    private final List<Recipe> recipes;

    public GetFavoriteRecipesWithIngredientsViewModel(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    @Override
    public String toString() {
        return "GetRecipesWithIngredientsViewModel{" +
                "recipes=" + recipes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GetFavoriteRecipesWithIngredientsViewModel that = (GetFavoriteRecipesWithIngredientsViewModel) o;

        return recipes != null ? recipes.equals(that.recipes) : that.recipes == null;
    }

    @Override
    public int hashCode() {
        return recipes != null ? recipes.hashCode() : 0;
    }

    public static class Recipe {

        private final String id;
        private final String name;
        private final int servings;
        private final String pictureUrl;
        private final List<Ingredient> ingredients;

        public Recipe(String id, String name, int servings, String pictureUrl, List<Ingredient> ingredients) {
            this.id = id;
            this.name = name;
            this.servings = servings;
            this.pictureUrl = pictureUrl;
            this.ingredients = ingredients;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getServings() {
            return servings;
        }

        public String getPictureUrl() {
            return pictureUrl;
        }

        public List<Ingredient> getIngredients() {
            return ingredients;
        }

        @Override
        public String toString() {
            return "Recipe{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", servings=" + servings +
                    ", pictureUrl='" + pictureUrl + '\'' +
                    ", ingredients=" + ingredients +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Recipe recipe = (Recipe) o;

            if (servings != recipe.servings) return false;
            if (id != null ? !id.equals(recipe.id) : recipe.id != null) return false;
            if (name != null ? !name.equals(recipe.name) : recipe.name != null) return false;
            if (pictureUrl != null ? !pictureUrl.equals(recipe.pictureUrl) : recipe.pictureUrl != null)
                return false;
            return ingredients != null ? ingredients.equals(recipe.ingredients) : recipe.ingredients == null;
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + servings;
            result = 31 * result + (pictureUrl != null ? pictureUrl.hashCode() : 0);
            result = 31 * result + (ingredients != null ? ingredients.hashCode() : 0);
            return result;
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

}
