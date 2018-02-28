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

package com.josemgu91.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.josemgu91.bakingapp.android.ui.DetailActivity;
import com.josemgu91.bakingapp.android.ui.MainActivity;
import com.josemgu91.bakingapp.android.ui.recipe_detail.RecipeDetailFragment;
import com.josemgu91.bakingapp.android.ui.recipes_list.RecipesFragment;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by jose on 2/28/18.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private IdlingResource idlingResource;

    @Rule
    public IntentsTestRule<MainActivity> mainActivityTestRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void clickRecipeElementOpensDetail() {
        registerRecipesFragmentIdlingResource();
        final ViewInteraction recyclerView = Espresso.onView(ViewMatchers.withId(R.id.recyclerview_recipes));
        recyclerView.perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        if (isInTablet()) {
            Intents.intended(
                    Matchers.allOf(
                            IntentMatchers.hasComponent(DetailActivity.class.getName()),
                            IntentMatchers.hasExtraWithKey(DetailActivity.PARAM_RECIPE_ID)
                    )
            );
        } else {
            Assert.assertTrue(
                    mainActivityTestRule
                            .getActivity()
                            .getSupportFragmentManager()
                            .findFragmentByTag(MainActivity.FRAGMENT_TAG_RECIPE_DETAIL_FRAGMENT)
                            != null);
        }
        unregisterIdlingResource();
    }

    @Test
    public void clickRecipeStepElementOpensStepDetail() {
        if (!isInTablet()) {
            registerRecipesFragmentIdlingResource();
            final ViewInteraction recyclerViewRecipes = Espresso.onView(ViewMatchers.withId(R.id.recyclerview_recipes));
            recyclerViewRecipes.perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
            unregisterIdlingResource();
            registerRecipeDetailFragmentIdlingResource();
            final ViewInteraction recyclerViewRecipeDetail = Espresso.onView(ViewMatchers.withId(R.id.recyclerview_recipe_detail));
            recyclerViewRecipeDetail.perform(RecyclerViewActions.actionOnItemAtPosition(
                    12,
                    ViewActions.click())
            );
            Assert.assertTrue(
                    mainActivityTestRule
                            .getActivity()
                            .getSupportFragmentManager()
                            .findFragmentByTag(MainActivity.FRAGMENT_TAG_RECIPE_STEP_DETAIL_FRAGMENT)
                            != null);
            unregisterIdlingResource();
        }
    }

    private void unregisterIdlingResource() {
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }

    private void registerRecipesFragmentIdlingResource() {
        final RecipesFragment recipesFragment = (RecipesFragment) mainActivityTestRule
                .getActivity()
                .getSupportFragmentManager()
                .findFragmentByTag(MainActivity.FRAGMENT_TAG_RECIPES_FRAGMENT);
        idlingResource = recipesFragment.getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    private void registerRecipeDetailFragmentIdlingResource() {
        final RecipeDetailFragment recipeDetailFragment = (RecipeDetailFragment) mainActivityTestRule
                .getActivity()
                .getSupportFragmentManager()
                .findFragmentByTag(MainActivity.FRAGMENT_TAG_RECIPE_DETAIL_FRAGMENT);
        idlingResource = recipeDetailFragment.getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    private boolean isInTablet() {
        return mainActivityTestRule
                .getActivity()
                .getResources()
                .getConfiguration()
                .smallestScreenWidthDp >= 600;
    }

}
