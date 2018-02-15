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
