package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TravelJournalTest {
    private TravelJournal testJournal;
    private SouvenirEntry testSouvenirEntry1;
    private SouvenirEntry testSouvenirEntry2;
    private SouvenirEntry testSouvenirEntry3;
    private SouvenirEntry testSouvenirEntry4;

    @BeforeEach
    public void initializeJournal() {
        testJournal = new TravelJournal();
        testSouvenirEntry1 = new SouvenirEntry("Italy", "03/04/21", "04/04/21");
        testSouvenirEntry2 = new SouvenirEntry("Spain", "02/10/21", "02/19/21");
        testSouvenirEntry3 = new SouvenirEntry("Norway", "02/10/20", "02/19/20");
        testSouvenirEntry4 = new SouvenirEntry("Sweden", "02/11/20", "02/12/20");
        testSouvenirEntry1.setNextJournalEntryNum(1);
    }

    @Test
    public void testMakeWelcomeString(){
        assertEquals(testJournal.makeWelcomeString("Joe"), "Welcome to Joe's Travel Journal!");
        assertEquals(testJournal.getName(), "Joe");
        assertEquals(testJournal.makeWelcomeString("Suzy"), "Welcome to Suzy's Travel Journal!");
        assertEquals(testJournal.getName(), "Suzy");
    }

    @Test
    public void testAddJournalEntry(){
        testJournal.addJournalEntry(testSouvenirEntry1);
        assertEquals(testJournal.getSize(), 1);
        assertEquals(testJournal.getJournalEntry(0), testSouvenirEntry1);


        testJournal.addJournalEntry(testSouvenirEntry2);
        assertEquals(testJournal.getSize(), 2);
        assertEquals(testJournal.getJournalEntry(1), testSouvenirEntry2);
    }

    @Test
    public void testFindJournalEntry(){
        testJournal.addJournalEntry(testSouvenirEntry1);
        assertEquals(testSouvenirEntry1.getJournalEntryNum(),1);
        testJournal.addJournalEntry(testSouvenirEntry2);
        assertEquals(testSouvenirEntry2.getJournalEntryNum(),2);
        testJournal.addJournalEntry(testSouvenirEntry3);
        assertEquals(testSouvenirEntry3.getJournalEntryNum(),3);
        testJournal.addJournalEntry(testSouvenirEntry4);
        assertEquals(testSouvenirEntry4.getJournalEntryNum(),4);

        checkIndex();

        assertEquals(testJournal.findJournalEntry(1), 0);
        assertEquals(testJournal.findJournalEntry(3), 2);
        assertEquals(testJournal.findJournalEntry(15), -1);
        assertEquals(testJournal.findJournalEntry(10), -1);


        assertEquals(testJournal.findJournalEntry(2), 1);
        assertTrue(testJournal.deleteJournalEntry(2));
        assertEquals(testJournal.findJournalEntry(2), -1);

        assertEquals(testJournal.findJournalEntry(4), 2);
        assertTrue(testJournal.deleteJournalEntry(4));
        assertEquals(testJournal.findJournalEntry(4), -1);

    }


    @Test
    public void testDeleteJournalEntry(){
        testJournal.addJournalEntry(testSouvenirEntry1);
        assertEquals(testSouvenirEntry1.getJournalEntryNum(),1);
        testJournal.addJournalEntry(testSouvenirEntry2);
        assertEquals(testSouvenirEntry2.getJournalEntryNum(),2);
        testJournal.addJournalEntry(testSouvenirEntry3);
        assertEquals(testSouvenirEntry3.getJournalEntryNum(),3);
        testJournal.addJournalEntry(testSouvenirEntry4);
        assertEquals(testSouvenirEntry4.getJournalEntryNum(),4);

        checkIndex();

        assertFalse(testJournal.deleteJournalEntry(9));
        assertTrue(testJournal.deleteJournalEntry(4));
        assertFalse(testJournal.deleteJournalEntry(5));
        assertTrue(testJournal.deleteJournalEntry(1));
        assertTrue(testJournal.deleteJournalEntry(2));
        assertTrue(testJournal.deleteJournalEntry(3));

    }

    public void checkIndex() {
        assertEquals(testJournal.getJournalEntry(0), testSouvenirEntry1);
        assertEquals(testJournal.getJournalEntry(1), testSouvenirEntry2);
        assertEquals(testJournal.getJournalEntry(2), testSouvenirEntry3);
        assertEquals(testJournal.getJournalEntry(3), testSouvenirEntry4);
    }

    @Test
    public void testToJson() {

        testJournal.addJournalEntry(testSouvenirEntry1);
        testJournal.addJournalEntry(testSouvenirEntry2);
        testJournal.addJournalEntry(testSouvenirEntry3);
        testJournal.addJournalEntry(testSouvenirEntry4);
        assertEquals(testJournal.getSize(), 4);

        testJournal.setName("Tina");
        assertEquals(testJournal.getName(), "Tina");

        JSONObject jsonJournal = testJournal.toJson();

        assertEquals(jsonJournal.getString("name"), "Tina");

        assertEquals(jsonJournal.getJSONArray("journal entries").getJSONObject(0).getString("location"),
                testSouvenirEntry1.getLocation());
        assertEquals(jsonJournal.getJSONArray("journal entries").getJSONObject(1).getString("location"),
                testSouvenirEntry2.getLocation());
        assertEquals(jsonJournal.getJSONArray("journal entries").getJSONObject(2).getString("location"),
                testSouvenirEntry3.getLocation());
        assertEquals(jsonJournal.getJSONArray("journal entries").getJSONObject(3).getString("location"),
                testSouvenirEntry4.getLocation());

        testJournal.deleteJournalEntry(1);
        jsonJournal = testJournal.toJson();
        assertEquals(jsonJournal.getJSONArray("journal entries").getJSONObject(0).getString("location"),
                testSouvenirEntry2.getLocation());
        assertEquals(jsonJournal.getJSONArray("journal entries").getJSONObject(1).getString("location"),
                testSouvenirEntry3.getLocation());
        assertEquals(jsonJournal.getJSONArray("journal entries").getJSONObject(2).getString("location"),
                testSouvenirEntry4.getLocation());


    }
}
