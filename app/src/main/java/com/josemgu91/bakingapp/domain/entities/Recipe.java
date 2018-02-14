package com.josemgu91.bakingapp.domain.entities;

import java.util.Collection;
import java.util.List;

/**
 * Created by jose on 2/14/18.
 */

public class Recipe {

    private final String id;
    private final String name;
    private final int servings;
    private final String pictureUrl;
    private final Collection<Ingredient> ingredients;
    private final List<Step> steps; //Ordered in sequence.

    public Recipe(String id, String name, int servings, String pictureUrl, Collection<Ingredient> ingredients, List<Step> steps) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.pictureUrl = pictureUrl;
        this.ingredients = ingredients;
        this.steps = steps;
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

    public Collection<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
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
        if (ingredients != null ? !ingredients.equals(recipe.ingredients) : recipe.ingredients != null)
            return false;
        return steps != null ? steps.equals(recipe.steps) : recipe.steps == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + servings;
        result = 31 * result + (pictureUrl != null ? pictureUrl.hashCode() : 0);
        result = 31 * result + (ingredients != null ? ingredients.hashCode() : 0);
        result = 31 * result + (steps != null ? steps.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", servings=" + servings +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                '}';
    }
}
