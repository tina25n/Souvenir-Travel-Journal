package persistence;

import model.SouvenirEntry;
import model.TravelJournal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

//This class closely models the JsonWriterTest class in the JsonSterilization Demo.
//Link to the demo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonWriterTest {
    TravelJournal testJournal;

    @BeforeEach
    public void initialize() {
        testJournal = new TravelJournal();
        testJournal.makeWelcomeString("Tina");
    }

    @Test
    public void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected.");
        } catch (IOException error) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyJournal() {
        try {
           JsonWriter writer = new JsonWriter("./data/testWriterEmptyJournal.json");
           writer.open();
           writer.write(testJournal);
           writer.close();

           JsonReader reader = new JsonReader("./data/testWriterEmptyJournal.json");
           testJournal = reader.read();
           assertEquals(testJournal.getSize(), 0);
           assertEquals(testJournal.getName(), "Tina");

        } catch (IOException error) {
            fail("IOException was not expected.");
        }

    }

    @Test
    public void testWriterJournal() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterJournal.json");
            SouvenirEntry testEntry = new SouvenirEntry("Rome", "01/03/21", "02/04/21");
            testEntry.getAttractsActsCategory().addSentenceToList("leaned", "tower of pisa");
            testEntry.getEateriesCategory().addSentenceToList("apple pie", "apple of my pie");
            testJournal.addJournalEntry(testEntry);
            assertEquals(testJournal.getName(), "Tina");
            writer.open();
            writer.write(testJournal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterJournal.json");
            testJournal = reader.read();
            assertEquals(testJournal.getName(), "Tina");
            assertEquals(testJournal.getJournalEntry(0).getLocation(), "Rome");
            assertEquals(testJournal.getJournalEntry(0).getEateriesCategory().getPlace(),
                    "apple of my pie");
            assertEquals(testJournal.getJournalEntry(0).getAttractsActsCategory().getActionItem(),
                    "leaned");
        } catch (IOException error) {
            fail("IOException was not expected.");
        }

    }
}
