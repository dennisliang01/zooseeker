package com.example.zooseeker_jj_zaaz_team_52;

import static org.junit.Assert.assertEquals;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import info.debatty.java.stringsimilarity.JaroWinkler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Test matching of user String input in exhibitSearch to Zoo exhibit information
 */
@RunWith(AndroidJUnit4.class)
public class ExhibitSearchTest {
    ExhibitSearch exhibitSearch;

    @Before
    public void setup() {
        exhibitSearch = new ExhibitSearch(ApplicationProvider.getApplicationContext().getString(R.string.sample_node_file), ApplicationProvider.getApplicationContext(), new JaroWinkler());
    }

    /**
     * Test match keywords to tags
     */
    @Test
    public void keywordMatchesTagTest() {
        List<ZooData.VertexInfo> result = exhibitSearch.searchKeyword("monkey");

        assertEquals("gorillas", result.get(0).id);
        assertEquals(1, result.size());
    }

    /**
     * Test entrance and exit gate is not shown as exhibit in search
     */
    @Test
    public void doNotShowExitTest() {
        List<ZooData.VertexInfo> result = exhibitSearch.searchKeyword("start");

        assertEquals(0, result.size());
    }

    /**
     * Test keyword returns multiple matching results
     */
    @Test
    public void matchMultipleTest() {
        List<ZooData.VertexInfo> results = exhibitSearch.searchKeyword("mammal");

        assertEquals(4, results.size());
    }

    /**
     * Test variance in results depending on threshold specified
     */
    @Test
    public void thresholdWorksTest() {
        List<ZooData.VertexInfo> results = exhibitSearch.searchKeyword("mamm", 0);

        assertEquals(0, results.size());
    }

    /**
     * Test results received from match of keyword substring
     */
    @Test
    public void matchCloseEnoughTest() {
        List<ZooData.VertexInfo> results = exhibitSearch.searchKeyword("eleph");

        assertEquals("elephant_odyssey", results.get(0).id);
        assertEquals(1, results.size());
    }

    /**
     * Test search suggestions correct for misspelling in input keyword
     */
    @Test
    public void testMisspellingCorrection() {
        List<ZooData.VertexInfo> results = exhibitSearch.searchKeyword("maama", .4);
        List<String> nameList = results.stream().map(vertexInfo -> vertexInfo.name)
                .collect(Collectors.toList());

        assertEquals(new ArrayList<>(
                        Arrays.asList("Elephant Odyssey", "Lions", "Arctic Foxes", "Gorillas")),
                nameList);
    }
}
