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

import com.josemgu91.bakingapp.android.ui.RecipeDetailActivity;
import com.josemgu91.bakingapp.android.ui.RecipeListActivity;
import com.josemgu91.bakingapp.android.ui.recipes_list.RecipesFragment;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by jose on 2/28/18.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest {

    private IdlingResource idlingResource;

    @Rule
    public IntentsTestRule<RecipeListActivity> mainActivityTestRule = new IntentsTestRule<>(RecipeListActivity.class);

    @Test
    public void clickRecipeElementOpensDetail() {
        registerRecipesFragmentIdlingResource();
        final ViewInteraction recyclerView = Espresso.onView(ViewMatchers.withId(R.id.recyclerview_recipes));
        recyclerView.perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        Intents.intended(
                Matchers.allOf(
                        IntentMatchers.hasComponent(RecipeDetailActivity.class.getName()),
                        IntentMatchers.hasExtraWithKey(RecipeDetailActivity.PARAM_RECIPE_ID),
                        IntentMatchers.hasExtraWithKey(RecipeDetailActivity.PARAM_RECIPE_NAME)
                )
        );
        unregisterIdlingResource();
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
                .findFragmentByTag(RecipeListActivity.FRAGMENT_TAG_RECIPES_FRAGMENT);
        idlingResource = recipesFragment.getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }
}
