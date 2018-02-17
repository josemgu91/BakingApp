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

import com.josemgu91.bakingapp.data.remote.RemoteRetrofitRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by jose on 2/15/18.
 */

public class RemoteRepositoryTest {

    private RemoteRetrofitRepository remoteRetrofitRepository;

    private final static String KNOWN_VALID_ID = "1";
    private final static String KNOWN_INVALID_ID = "0";

    @Before
    public void initRepository() {
        remoteRetrofitRepository = new RemoteRetrofitRepository();
    }

    @Test
    public void testRecipesDataGateway() throws Exception {
        assertListNotEmpty(remoteRetrofitRepository.getRecipes());
    }

    @Test
    public void testStepsDataGateway() throws Exception {
        assertListNotEmpty(remoteRetrofitRepository.getSteps(KNOWN_VALID_ID));

    }

    @Test
    public void testIngredientsDataGateway() throws Exception {
        assertListNotEmpty(remoteRetrofitRepository.getIngredients(KNOWN_VALID_ID));
    }

    @Test
    public void testStepsNotFoundDataGateway() throws Exception {
        assertListEmpty(remoteRetrofitRepository.getSteps(KNOWN_INVALID_ID));
    }

    @Test
    public void testIngredientsNotFoundDataGateway() throws Exception {
        assertListEmpty(remoteRetrofitRepository.getIngredients(KNOWN_INVALID_ID));
    }

    private void assertListNotEmpty(final List list) {
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

    private void assertListEmpty(final List list) {
        Assert.assertEquals(0, list.size());
    }

}
