package com.josemgu91.bakingapp.domain.usecases;

/**
 * Created by jose on 2/14/18.
 */

public class RecipeOutput {

    private final String id;
    private final String name;
    private final int servings;
    private final String pictureUrl;

    public RecipeOutput(String id, String name, int servings, String pictureUrl) {
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
}
