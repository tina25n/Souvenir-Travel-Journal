package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AttractionsAndActivitiesCategoryTest {
    private AttractionsAndActivitiesCategory testAttractionsAndActivitiesCategory;

    @BeforeEach
    public void initialize() {
        testAttractionsAndActivitiesCategory = new AttractionsAndActivitiesCategory();
    }

    @Test
    public void testInitialize() {
        assertEquals(testAttractionsAndActivitiesCategory.getSize(), 0);
    }

    @Test
    public void testAddSentenceToAttractionsAndActivitiesList() {
        testAttractionsAndActivitiesCategory.addSentenceToList("swam", "Santorini");
        assertEquals(testAttractionsAndActivitiesCategory.getPlace(), "Santorini");
        assertEquals(testAttractionsAndActivitiesCategory.getActionItem(), "swam");
        assertEquals(testAttractionsAndActivitiesCategory.getSize(), 1);


        testAttractionsAndActivitiesCategory.addSentenceToList("drew", "Rome");
        assertEquals(testAttractionsAndActivitiesCategory.getIndexNum(),2);
        testAttractionsAndActivitiesCategory.addSentenceToList("partied", "Ibiza");
        assertEquals(testAttractionsAndActivitiesCategory.getIndexNum(),3);
        testAttractionsAndActivitiesCategory.addSentenceToList("sang", "France");
        assertEquals(testAttractionsAndActivitiesCategory.getSize(), 4);
        assertEquals(testAttractionsAndActivitiesCategory.getIndexNum(),4);
    }

    @Test
    public void testRemoveSentenceFromAttractionsAndActivitiesList() {
        testAttractionsAndActivitiesCategory.addSentenceToList("swam", "Santorini");
        testAttractionsAndActivitiesCategory.addSentenceToList("fell", "SkyTower");
        testAttractionsAndActivitiesCategory.addSentenceToList("drew", "Museum");
        assertEquals(testAttractionsAndActivitiesCategory.getIndexNum(),3);
        assertEquals(testAttractionsAndActivitiesCategory.getSize(), 3);

        assertTrue(testAttractionsAndActivitiesCategory.removeSentenceFromList(2));
        assertEquals(testAttractionsAndActivitiesCategory.getSize(), 2);
        assertTrue(testAttractionsAndActivitiesCategory.removeSentenceFromList(1));
        assertEquals(testAttractionsAndActivitiesCategory.getSize(),1);

        assertFalse(testAttractionsAndActivitiesCategory.removeSentenceFromList(7));
        assertEquals(testAttractionsAndActivitiesCategory.getSize(),1);

        assertFalse(testAttractionsAndActivitiesCategory.removeSentenceFromList(9));
        assertEquals(testAttractionsAndActivitiesCategory.getSize(), 1);
    }

    @Test
    public void testAddThenRemoveThenAddSentences() {
        testAttractionsAndActivitiesCategory.addSentenceToList("sang", "SkyTower");
        testAttractionsAndActivitiesCategory.addSentenceToList("partied", "Ibiza");
        testAttractionsAndActivitiesCategory.addSentenceToList("slept", "Hotel Whitcomb");
        testAttractionsAndActivitiesCategory.addSentenceToList("danced", "Tower of Piza");
        assertEquals(testAttractionsAndActivitiesCategory.getSize(), 4);

        assertTrue(testAttractionsAndActivitiesCategory.removeSentenceFromList(3));
        assertEquals(testAttractionsAndActivitiesCategory.getSize(), 3);

        assertTrue(testAttractionsAndActivitiesCategory.removeSentenceFromList(1));
        assertEquals(testAttractionsAndActivitiesCategory.getSize(), 2);

        testAttractionsAndActivitiesCategory.addSentenceToList("walked", "Eiffel Tower");
        assertEquals(testAttractionsAndActivitiesCategory.getIndexNum(),5);
        testAttractionsAndActivitiesCategory.addSentenceToList("saw fishies", "Aquarium");
        assertEquals(testAttractionsAndActivitiesCategory.getIndexNum(),6);
        assertEquals(testAttractionsAndActivitiesCategory.getSize(), 4);

        assertFalse(testAttractionsAndActivitiesCategory.removeSentenceFromList(7));
        assertEquals(testAttractionsAndActivitiesCategory.getSize(), 4);

    }

    @Test
    public void testGetSentenceInList(){
        testAttractionsAndActivitiesCategory.addSentenceToList("sang", "SkyTower");
        testAttractionsAndActivitiesCategory.addSentenceToList("partied", "Ibiza");

        assertEquals(testAttractionsAndActivitiesCategory.getSentenceInList(0), "1: I sang at SkyTower.");
        assertEquals(testAttractionsAndActivitiesCategory.getSentenceInList(1), "2: I partied at Ibiza.");
    }

    @Test
    public void testToJson () {
        testAttractionsAndActivitiesCategory.addSentenceToList("partied", "Ibiza");

        JSONObject testAttractAndActInfo = testAttractionsAndActivitiesCategory.toJson();

        assertEquals(testAttractAndActInfo.getJSONArray("attracts and acts info").getJSONObject(0).getString("action"),
                testAttractionsAndActivitiesCategory.getActionItem());
        assertEquals(testAttractAndActInfo.getJSONArray("attracts and acts info").getJSONObject(0).getString("place"),
                testAttractionsAndActivitiesCategory.getPlace());

        testAttractionsAndActivitiesCategory.addSentenceToList("sang", "SkyTower");
        assertEquals(testAttractionsAndActivitiesCategory.getSize(), 2);

        testAttractAndActInfo = testAttractionsAndActivitiesCategory.toJson();

        assertEquals(testAttractAndActInfo.getJSONArray("attracts and acts info").getJSONObject(1).getString("action"),
                testAttractionsAndActivitiesCategory.getActionItem());
        assertEquals(testAttractAndActInfo.getJSONArray("attracts and acts info").getJSONObject(1).getString("place"),
                testAttractionsAndActivitiesCategory.getPlace());

        assertTrue(testAttractionsAndActivitiesCategory.removeSentenceFromList(1));
        testAttractAndActInfo = testAttractionsAndActivitiesCategory.toJson();
        assertEquals(testAttractAndActInfo.getJSONArray("attracts and acts info").getJSONObject(0).getString("action"),
                testAttractionsAndActivitiesCategory.getActionItem());
        assertEquals(testAttractAndActInfo.getJSONArray("attracts and acts info").getJSONObject(0).getString("place"),
                testAttractionsAndActivitiesCategory.getPlace());
    }

}
