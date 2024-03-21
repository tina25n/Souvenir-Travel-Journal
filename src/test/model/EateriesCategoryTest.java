package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EateriesCategoryTest {
    private EateriesCategory testEateriesCategory;

    @BeforeEach
    public void initialize() {
        testEateriesCategory = new EateriesCategory();
    }

    @Test
    public void testInitialize() {
        assertEquals(testEateriesCategory.getSize(), 0);
    }

    @Test
    public void testAddSentenceToAttractionsAndActivitiesList() {
        testEateriesCategory.addSentenceToList("hot dog", "JapaDog");
        assertEquals(testEateriesCategory.getPlace(), "JapaDog");
        assertEquals(testEateriesCategory.getActionItem(), "hot dog");
        assertEquals(testEateriesCategory.getSize(), 1);
        assertEquals(testEateriesCategory.getIndexNum(),1);

        testEateriesCategory.addSentenceToList("Lollipops", "BonBon");
        assertEquals(testEateriesCategory.getIndexNum(),2);
        testEateriesCategory.addSentenceToList("Candy apples", "Apple of My Eye");
        assertEquals(testEateriesCategory.getSize(), 3);
        assertEquals(testEateriesCategory.getIndexNum(),3);

    }

    @Test
    public void testRemoveSentenceFromAttractionsAndActivitiesList() {
        testEateriesCategory.addSentenceToList("calamari", "Santorini Paradise");
        testEateriesCategory.addSentenceToList("pie", "pie love you");
        testEateriesCategory.addSentenceToList("yogurt", "fro yo mama");
        assertEquals(testEateriesCategory.getSize(), 3);
        assertEquals(testEateriesCategory.getIndexNum(),3);

        assertTrue(testEateriesCategory.removeSentenceFromList(2));
        assertEquals(testEateriesCategory.getSize(), 2);

        assertFalse(testEateriesCategory.removeSentenceFromList(9));
        assertEquals(testEateriesCategory.getSize(), 2);

        assertTrue(testEateriesCategory.removeSentenceFromList(1));
        assertEquals(testEateriesCategory.getSize(), 1);
    }

    @Test
    public void testAddthenRemoveThenAddSentences(){
        testEateriesCategory.addSentenceToList("calamari", "Santorini Paradise");
        testEateriesCategory.addSentenceToList("yogurt", "fro yo mama");
        testEateriesCategory.addSentenceToList("burger", "bob's burgers");
        assertEquals(testEateriesCategory.getIndexNum(),3);
        assertEquals(testEateriesCategory.getSize(), 3);

        assertTrue(testEateriesCategory.removeSentenceFromList(2));
        assertEquals(testEateriesCategory.getSize(), 2);

        assertTrue(testEateriesCategory.removeSentenceFromList(1));
        assertEquals(testEateriesCategory.getSize(), 1);

        testEateriesCategory.addSentenceToList("pie", "pie love you");
        assertEquals(testEateriesCategory.getIndexNum(),4);
        assertEquals(testEateriesCategory.getSize(), 2);

        testEateriesCategory.addSentenceToList("noodles", "noodle home");
        assertEquals(testEateriesCategory.getIndexNum(),5);
        assertEquals(testEateriesCategory.getSize(), 3);
    }

    @Test
    public void testGetSentenceInList(){
        testEateriesCategory.addSentenceToList("pie", "pie love you");
        testEateriesCategory.addSentenceToList("yogurt", "fro yo mama");

        assertEquals(testEateriesCategory.getSentenceInList(0), "1: I had pie at pie love you.");
        assertEquals(testEateriesCategory.getSentenceInList(1), "2: I had yogurt at fro yo mama.");
    }

    @Test
    public void testToJson () {
        testEateriesCategory.addSentenceToList("pie", "pie love you");

        JSONObject testEateryInfo = testEateriesCategory.toJson();

        assertEquals(testEateryInfo.getJSONArray("eateries info").getJSONObject(0).getString("action"),
                testEateriesCategory.getActionItem());
        assertEquals(testEateryInfo.getJSONArray("eateries info").getJSONObject(0).getString("place"),
                testEateriesCategory.getPlace());
        String lastFood = testEateriesCategory.getActionItem();
        String lastEatery = testEateriesCategory.getPlace();

        testEateriesCategory.addSentenceToList("yogurt", "fro yo mama");
        testEateryInfo = testEateriesCategory.toJson();

        assertEquals(testEateryInfo.getJSONArray("eateries info").getJSONObject(1).getString("action"),
                testEateriesCategory.getActionItem());
        assertEquals(testEateryInfo.getJSONArray("eateries info").getJSONObject(1).getString("place"),
                testEateriesCategory.getPlace());

        assertTrue(testEateriesCategory.removeSentenceFromList(2));
        assertEquals(testEateryInfo.getJSONArray("eateries info").getJSONObject(0).getString("action"),
                lastFood);
        assertEquals(testEateryInfo.getJSONArray("eateries info").getJSONObject(0).getString("place"),
                lastEatery);


    }


}
