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
