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
 * Created by jose on 2/14/18.
 */

public class GetRecipesViewModel {

    private final List<Recipe> recipes;

    public GetRecipesViewModel(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    @Override
    public String toString() {
        return "GetRecipesViewModel{" +
                "recipes=" + recipes +
                '}';
    }

    public static class Recipe {

        private final String id;
        private final String name;
        private final int servings;
        private final String pictureUrl;

        public Recipe(String id, String name, int servings, String pictureUrl) {
            this.id = id;
            this.name = name;
            this.servings = servings;
            this.pictureUrl = pictureUrl;
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

        @Override
        public String toString() {
            return "Recipe{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", servings=" + servings +
                    ", pictureUrl='" + pictureUrl + '\'' +
                    '}';
        }
    }

}
