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

package com.josemgu91.bakingapp.domain.usecases;

import java.util.List;

/**
 * Created by jose on 2/14/18.
 */

public class RecipeWithIngredientsOutput {

    private final String id;
    private final String name;
    private final int servings;
    private final String pictureUrl;
    private final List<Ingredient> ingredients;

    public RecipeWithIngredientsOutput(String id, String name, int servings, String pictureUrl, List<Ingredient> ingredients) {
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
    }

}
