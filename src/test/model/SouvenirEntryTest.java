package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SouvenirEntryTest {
    private SouvenirEntry testSouvenirEntry;

    @BeforeEach
    public void initializeEntry() {
        testSouvenirEntry = new SouvenirEntry("China", "09/31/21", "10/31/21");
        testSouvenirEntry.setNextJournalEntryNum(1);
    }

    @Test
    public void testInitialization() {
        assertEquals(testSouvenirEntry.getEndDate(), "10/31/21");
        assertEquals(testSouvenirEntry.getStartDate(), "09/31/21");
        assertEquals(testSouvenirEntry.getLocation(), "China");
        assertEquals(testSouvenirEntry.getJournalEntryNum(), 1);
    }

    @Test
    public void testTitleOfEntry() {
        assertEquals(testSouvenirEntry.titleOfEntry(), "1 My trip to China (09/31/21-10/31/21)");
    }

    @Test
    public void testGetEateriesCategory(){
        EateriesCategory testEC = testSouvenirEntry.getEateriesCategory();
        assertNotNull(testEC);
    }

    @Test
    public void testGetAttractsAndActsCategory(){
        AttractionsAndActivitiesCategory testAAC = testSouvenirEntry.getAttractsActsCategory();
        assertNotNull(testAAC);
    }

    @Test
    public void testToJson () {
        testSouvenirEntry.getAttractsActsCategory().addSentenceToList("sang", "karaoke-doke");
        testSouvenirEntry.getEateriesCategory().addSentenceToList("poke", "poke-mon");

        JSONObject jsonTestEntry = testSouvenirEntry.toJson();
        assertEquals(jsonTestEntry.getString("start date"), testSouvenirEntry.getStartDate());
        assertEquals(jsonTestEntry.getString("end date"), testSouvenirEntry.getEndDate());
        assertEquals(jsonTestEntry.getString("location"), testSouvenirEntry.getLocation());

        assertEquals(jsonTestEntry.getJSONObject("eateries list").getJSONArray("eateries info").getJSONObject(0).getString("action"),
                testSouvenirEntry.getEateriesCategory().getActionItem());
        assertEquals(jsonTestEntry.getJSONObject("eateries list").getJSONArray("eateries info").getJSONObject(0).getString("place"),
                testSouvenirEntry.getEateriesCategory().getPlace());

        assertEquals(jsonTestEntry.getJSONObject("attracts and acts list").getJSONArray("attracts and acts info").getJSONObject(0).getString("action"),
                testSouvenirEntry.getAttractsActsCategory().getActionItem());
        assertEquals(jsonTestEntry.getJSONObject("attracts and acts list").getJSONArray("attracts and acts info").getJSONObject(0).getString("place"),
                testSouvenirEntry.getAttractsActsCategory().getPlace());
    }
}
